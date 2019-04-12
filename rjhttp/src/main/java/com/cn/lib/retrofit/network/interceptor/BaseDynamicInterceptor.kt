package com.cn.lib.retrofit.network.interceptor

import com.cn.lib.retrofit.network.util.LogUtil
import com.cn.lib.retrofit.network.util.Util
import okhttp3.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

abstract class BaseDynamicInterceptor<R : BaseDynamicInterceptor<R>> : Interceptor {
    var httpUrl: HttpUrl? = null
        private set

    var isSign = false
        private set    //是否需要签名
    var isTimeStamp = false
        private set    //是否需要追加时间戳
    var isAccessToken = false
        private set    //是否需要添加token


    fun sign(sign: Boolean): R {
        isSign = sign
        return this as R
    }

    fun timeStamp(timeStamp: Boolean): R {
        this.isTimeStamp = timeStamp
        return this as R
    }

    fun accessToken(accessToken: Boolean): R {
        this.isAccessToken = accessToken
        return this as R
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.method() == "GET") {
            this.httpUrl = HttpUrl.parse(parseUrl(request.url().url().toString()))
            request = addGetParamsSign(request)
        } else if (request.method() == "POST") {
            this.httpUrl = request.url()
            request = addPostParamsSign(request)
        }
        return chain.proceed(request)
    }

    //get 添加签名和公共动态参数
    @Throws(UnsupportedEncodingException::class)
    private fun addGetParamsSign(request: Request): Request {
        var request = request
        var httpUrl = request.url()
        val newBuilder = httpUrl.newBuilder()

        //获取原有的参数
        val nameSet = httpUrl.queryParameterNames()
        val nameList = ArrayList<String>()
        nameList.addAll(nameSet)
        val oldparams = TreeMap<String, String>()
        for (i in nameList.indices) {
            val value = if (httpUrl.queryParameterValues(nameList[i]) != null && httpUrl.queryParameterValues(nameList[i]).size > 0) httpUrl.queryParameterValues(nameList[i])[0] else ""
            oldparams[nameList[i]] = value
        }
        val nameKeys = listOf(nameList).toString()
        //拼装新的参数
        val newParams = dynamic(oldparams)
        Util.checkNotNull<Any>(newParams, "newParams==null")
        for ((key, value) in newParams) {
            val urlValue = URLEncoder.encode(value, "UTF-8")
            if (!nameKeys.contains(key)) {//避免重复添加
                newBuilder.addQueryParameter(key, urlValue)
            }
        }

        httpUrl = newBuilder.build()
        request = request.newBuilder().url(httpUrl).build()
        return request
    }

    //templatePost 添加签名和公共动态参数
    @Throws(UnsupportedEncodingException::class)
    private fun addPostParamsSign(request: Request): Request {
        var request = request
        if (request.body() is FormBody) {
            val bodyBuilder = FormBody.Builder()
            var formBody: FormBody = request.body() as FormBody

            //原有的参数
            val oldparams = TreeMap<String, String>()
            for (i in 0 until formBody.size()) {
                oldparams[formBody.encodedName(i)] = formBody.encodedValue(i)
            }

            //拼装新的参数
            val newParams = dynamic(oldparams)
            Util.checkNotNull<Any>(newParams, "newParams==null")
            //Logc.i("======post请求参数===========");
            for ((key, value1) in newParams) {
                val value = URLEncoder.encode(value1, "UTF-8")
                bodyBuilder.addEncoded(key, value)
                //Logc.i(entry.getKey() + " -> " + value);
            }
            val url = createUrlFromParams(httpUrl!!.url().toString(), newParams)
            LogUtil.i(url)
            formBody = bodyBuilder.build()
            request = request.newBuilder().post(formBody).build()
        } else if (request.body() is MultipartBody) {
            var multipartBody: MultipartBody = request.body() as MultipartBody
            val bodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            val oldparts = multipartBody.parts()

            //拼装新的参数
            val newparts = ArrayList<MultipartBody.Part>()
            newparts.addAll(oldparts)
            val oldparams = TreeMap<String, String>()
            val newParams = dynamic(oldparams)
            for ((key, value) in newParams) {
                val part = MultipartBody.Part.createFormData(key, value)
                newparts.add(part)
            }
            for (part in newparts) {
                bodyBuilder.addPart(part)
            }
            multipartBody = bodyBuilder.build()
            request = request.newBuilder().post(multipartBody).build()
        }
        return request
    }

    //解析前：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult?appId=10101
    //解析后：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult
    private fun parseUrl(url: String): String {
        var url = url
        if ("" != url && url.contains("?")) {// 如果URL不是空字符串
            url = url.substring(0, url.indexOf('?'))
        }
        return url
    }

    private fun createUrlFromParams(url: String, params: Map<String, String>): String {
        try {
            val sb = StringBuilder()
            sb.append(url)
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0)
                sb.append("&")
            else
                sb.append("?")
            for ((key, urlValues) in params) {
//对参数进行 utf-8 编码,防止头信息传中文
                //String urlValue = URLEncoder.encode(urlValues, UTF8.name());
                sb.append(key).append("=").append(urlValues).append("&")
            }
            sb.deleteCharAt(sb.length - 1)
            return sb.toString()
        } catch (e: Exception) {
            LogUtil.e(e.message)
        }

        return url
    }


    /**
     * 动态处理参数
     *
     * @param dynamicMap
     * @return 返回新的参数集合
     */
    abstract fun dynamic(dynamicMap: TreeMap<String, String>): TreeMap<String, String>

}
