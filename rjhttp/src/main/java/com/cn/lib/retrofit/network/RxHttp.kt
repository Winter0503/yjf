package com.cn.lib.retrofit.network


import android.content.Context
import com.cn.lib.retrofit.network.interceptor.HeaderInterceptor
import com.cn.lib.retrofit.network.request.*
import com.cn.lib.retrofit.network.util.LogUtil
import com.cn.lib.retrofit.network.util.SSLUtil
import com.cn.lib.retrofit.network.util.Util
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.io.InputStream
import java.net.Proxy
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

open class RxHttp private constructor() {
    private val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()                 //okhttp请求的客户端
    internal val retrofitBuilder: Retrofit.Builder                         //Retrlofit请求Builder
    private var mCacheMaxSize: Long = 0                                       //最大缓存
    private val headers = HashMap<String, String>()            //公共请求头
    private val parameters = HashMap<String, String>()         //公共请求参数
    var httpClient: OkHttpClient? = null
        private set                                  //自定义OkHttpClient
    var retryCount = DEFAULT_RETRY_COUNT
        private set                    //重试次数默认3次
    var retryDelay = DEFAULT_RETRY_DELAY
        private set                    //延迟xxms重试
    var retryIncreaseDelay = DEFAULT_RETRY_INCREASEDELAY
        private set    //叠加延迟
    private val interceptorList = ArrayList<Interceptor>()
    private val networkInterceptorList = ArrayList<Interceptor>()
    private lateinit var context: Context
    var baseUrl: String? = null
        private set
    var isSign = false
        private set
    var isAccessToken = false
        private set
    var isSyncRequest = true
        private set
    var baseHeaderInterceptor: HeaderInterceptor? = null
        private set


    val okHttpClient: OkHttpClient
        get() = getOkHttpClientBuilder().build()

    val retrofit: Retrofit
        get() = retrofitBuilder.build()

    fun init(context: Context): RxHttp {
        this.context = context
        return this
    }


    init {
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS.toLong(), TimeUnit.SECONDS)
        retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())//增加RxJava2CallAdapterFactory
    }

    /**
     * 全局设置访问域
     */
    fun baseUrl(baseUrl: String): RxHttp {
        this.baseUrl = baseUrl
        retrofitBuilder.baseUrl(Util.checkNotNull(baseUrl, "OkHttpClient is null"))
        return this
    }

    /**
     * 全局设置自定义OkHttpClient
     */
    fun OkHttpClient(okHttpClient: OkHttpClient): RxHttp {
        retrofitBuilder.client(Util.checkNotNull(okHttpClient, "OkHttpClient is null"))
        return this
    }

    /**
     * 全局设置日志输出
     */
    fun isLog(log: Boolean): RxHttp {
        LogUtil.isDebug = log
        // Log信息拦截器
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //设置 Debug Log 模式
        this.okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return this
    }

    /**
     * 全局网络拦截器
     */
    fun addNetworkInterceptor(interceptor: Interceptor): RxHttp {
        if (!networkInterceptorList.contains(Util.checkNotNull(interceptor, "interceptor is null"))) {
            networkInterceptorList.add(interceptor)
        }
        return this
    }

    /**
     * 全局拦截器
     */
    fun addInterceptor(interceptor: Interceptor?): RxHttp {
        if (!interceptorList.contains(Util.checkNotNull(interceptor, "interceptor is null"))) {
            interceptor?.run {
                if (this is HeaderInterceptor)
                    interceptorList.add(0, this)
                else
                    interceptorList.add(this)
            }
        }
        return this
    }

    /**
     * 全局缓存
     */
    fun cache(cache: Cache): RxHttp {
        okHttpClientBuilder.cache(Util.checkNotNull(cache, "cache is null"))
        return this
    }

    /**
     * 缓存文件大小
     */
    fun cacheMaxSize(cacheMaxSize: Long): RxHttp {
        this.mCacheMaxSize = cacheMaxSize
        return this
    }

    /**
     * 全局缓存文件
     */
    fun cacheFile(cacheFile: File): RxHttp {
        val cache = Cache(Util.checkNotNull(cacheFile, "cacheFile is null"), Math.max((5 * 1024 * 1024).toLong(), mCacheMaxSize))
        return cache(cache)
    }

    /**
     * 全局OkHttp的代理
     */
    fun okProxy(proxy: Proxy): RxHttp {
        okHttpClientBuilder.proxy(proxy)
        return this
    }

    /**
     * https的全局访问规则
     */
    fun hostnameVerifier(hostnameVerifier: HostnameVerifier): RxHttp {
        okHttpClientBuilder.hostnameVerifier(Util.checkNotNull(hostnameVerifier, "HostnameVerifier is null"))
        return this
    }

    /**
     * 全局设置Converter.Factory
     */
    fun converterFactory(converterFactory: Converter.Factory): RxHttp {
        retrofitBuilder.addConverterFactory(Util.checkNotNull(converterFactory, "Converter.Factory is null"))
        return this
    }

    /**
     * 全局设置CallAdapter.Factory,默认RxJavaCallAdapterFactory.create()
     */
    fun callAdapterFactory(callAdapterFactory: CallAdapter.Factory): RxHttp {
        retrofitBuilder.addCallAdapterFactory(Util.checkNotNull(callAdapterFactory, "CallAdapter.Factory is null"))
        return this
    }

    /**
     * 全局设置Retrofit对象Factory
     */
    fun setCallFactory(factory: okhttp3.Call.Factory): RxHttp {
        retrofitBuilder.callFactory(Util.checkNotNull(factory, "factory == null"))
        return this
    }

    /**
     * https的全局自签名证书
     */
    fun certificates(vararg certificates: InputStream): RxHttp {
        val sslParams = SSLUtil.getSslSocketFactory(null, null, *certificates)
        okHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
        return this
    }

    /**
     * https双向认证证书
     */
    fun certificates(bksFile: InputStream, password: String, vararg certificates: InputStream): RxHttp {
        val sslParams = SSLUtil.getSslSocketFactory(bksFile, password, *certificates)
        okHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
        return this
    }

    fun sslSocketFactory(sslSocketFactory: SSLSocketFactory): RxHttp {
        okHttpClientBuilder.sslSocketFactory(sslSocketFactory)
        return this
    }


    fun trustManager(sslSocketFactory: SSLSocketFactory, trustManager: X509TrustManager): RxHttp {
        okHttpClientBuilder.sslSocketFactory(sslSocketFactory, trustManager)
        return this
    }

    /**
     * 全局cookie存取规则
     */
    fun cookieJar(cookieJar: CookieJar): RxHttp {
        okHttpClientBuilder.cookieJar(cookieJar)
        return this
    }

    /**
     * 全局设置请求的连接池
     */
    fun connectionPool(connectionPool: ConnectionPool): RxHttp {
        okHttpClientBuilder.connectionPool(connectionPool)
        return this
    }

    fun getHeaders(): Map<String, String> {
        return headers
    }

    /**
     * 全局设置请求头
     */
    fun headers(headers: Map<String, String>): RxHttp {
        this.headers.putAll(headers)
        return this
    }

    fun removeHeader(key: String): RxHttp {
        if (headers.containsKey(key)) {
            headers.remove(key)
        }
        this.baseHeaderInterceptor?.remove(key)
        return this
    }

    fun clearAllHeaders(): RxHttp {
        headers.clear()
        this.baseHeaderInterceptor?.clearAll()
        return this
    }

    /**
     * 添加全局公共请求参数
     */
    fun addHeader(key: String, value: String): RxHttp {
        this.headers[key] = value
        return this
    }

    fun getParameters(): Map<String, String> {
        return parameters
    }

    fun parameters(parameters: Map<String, String>): RxHttp {
        this.parameters.putAll(parameters)
        return this
    }

    fun addParam(key: String, value: String): RxHttp {
        this.parameters[key] = value
        return this
    }

    /**
     * 全局为Retrofit设置自定义的OkHttpClient
     */
    fun httpClient(httpClient: OkHttpClient): RxHttp {
        this.httpClient = httpClient
        return this
    }

    /**
     * 全局设置读超时时间
     */
    fun readTimeout(readTimeout: Long): RxHttp {
        okHttpClientBuilder.readTimeout(readTimeout, TimeUnit.SECONDS)
        return this
    }

    /**
     * 全局设置写超时时间
     */
    fun writeTimeout(writeTimeout: Int): RxHttp {
        okHttpClientBuilder.writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
        return this
    }

    /**
     * 全局设置连接超时时间
     */
    fun connectTimeout(connectTimeout: Int): RxHttp {
        okHttpClientBuilder.connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
        return this
    }

    fun getInterceptorList(): List<Interceptor> {
        return interceptorList
    }

    fun interceptorList(interceptorList: List<Interceptor>): RxHttp {
        this.interceptorList.addAll(interceptorList)
        return this
    }

    fun getNetworkInterceptorList(): List<Interceptor> {
        return networkInterceptorList
    }

    fun networkInterceptorList(networkInterceptorList: List<Interceptor>): RxHttp {
        this.networkInterceptorList.addAll(networkInterceptorList)
        return this
    }

    fun getContext(): Context {
        return context
    }

    fun isSign(isSign: Boolean): RxHttp {
        this.isSign = isSign
        return this
    }

    /**
     * 全局设置是否是异步请求
     */
    fun isSyncRequest(isSyncRequest: Boolean): RxHttp {
        this.isSyncRequest = isSyncRequest
        return this
    }

    fun accessToken(accessToken: Boolean) {
        this.isAccessToken = accessToken
    }

    /**
     * 超时重试次数
     */
    fun retryCount(retryCount: Int): RxHttp {
        if (retryCount < 0) throw IllegalArgumentException("retryCount must > 0")
        this.retryCount = retryCount
        return this
    }

    /**
     * 超时重试延迟时间
     */
    fun retryDelay(retryDelay: Int): RxHttp {
        if (retryDelay < 0) throw IllegalArgumentException("retryDelay must > 0")
        this.retryDelay = retryDelay
        return this
    }

    /**
     * 超时重试延迟叠加时间
     */
    fun retryIncreaseDelay(retryIncreaseDelay: Int): RxHttp {
        if (retryIncreaseDelay < 0)
            throw IllegalArgumentException("retryIncreaseDelay must > 0")
        this.retryIncreaseDelay = retryIncreaseDelay
        return this
    }

    fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        addHeaderInterceptor()
        if (interceptorList.size > 0) {
            for (interceptor in interceptorList) {
                if (!okHttpClientBuilder.interceptors().contains(interceptor))
                    okHttpClientBuilder.addInterceptor(interceptor)
            }
        }
        if (networkInterceptorList.size > 0) {
            for (interceptor in networkInterceptorList) {
                if (!okHttpClientBuilder.interceptors().contains(interceptor))
                    okHttpClientBuilder.addNetworkInterceptor(interceptor)
            }
        }
        return okHttpClientBuilder
    }

    private fun addHeaderInterceptor() {
        //加入请求参数以及头信息
        if (headers.size > 0) {
            baseHeaderInterceptor?.run {
                addHeaderMap(headers)
                return
            }
            baseHeaderInterceptor = HeaderInterceptor(headers)
            //将添加统一头内容的拦截器放在第一位方便后面的拦截器使用
            addInterceptor(baseHeaderInterceptor)
        }
    }

    companion object {
        private val TAG = RxHttp::class.java.simpleName
        val DEFAULT_MILLISECONDS = 30             //默认的超时时间
        private val DEFAULT_RETRY_COUNT = 3                 //默认重试次数
        private val DEFAULT_RETRY_INCREASEDELAY = 0         //默认重试叠加时间
        private val DEFAULT_RETRY_DELAY = 500               //默认重试延时
        private val CACHE_MAX_SIZE = (10 * 1024 * 1024).toLong()      //默认缓存大小

        val INSTANCE: RxHttp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RxHttp() }

        fun templatePost(url: String): TemplatePostRequest {
            return TemplatePostRequest(url)
        }

        fun templateDelete(url: String): TemplateDeleteRequest {
            return TemplateDeleteRequest(url)
        }
        fun templateGet(url: String): TemplateGetRequest {
            return TemplateGetRequest(url)
        }
        fun templatePut(url: String): TemplatePutRequest {
            return TemplatePutRequest(url)
        }

        fun resultPost(url: String): ApiResultPostRequest {
            return ApiResultPostRequest(url)
        }

        fun resultGet(url:String):ApiResultGetRequest{
            return ApiResultGetRequest(url)
        }
        fun resultDelete(url:String):ApiResultDeleteRequest{
            return ApiResultDeleteRequest(url)
        }
        fun resultPut(url:String):ApiResultPutRequest{
            return ApiResultPutRequest(url)
        }


        fun upload(url: String): UploadRequest {
            return UploadRequest(url)
        }

        fun download(url: String): DownloadRequest {
            return DownloadRequest(url)
        }
    }
}

