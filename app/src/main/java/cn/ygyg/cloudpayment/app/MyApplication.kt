package cn.ygyg.cloudpayment.app

import android.annotation.SuppressLint
import android.app.Application
import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory
import com.cn.lib.retrofit.network.RxHttp
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory


/**
 * Created by Admin on 2019/4/13.
 */
class MyApplication : Application() {
    open lateinit var mWxApi: IWXAPI
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mApp: MyApplication
        fun getApplication(): MyApplication {
            return mApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApp = this
        initRequestBase()
        registerToWX()
    }

    private fun registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WX.WEIXIN_APP_ID, false)
        // 将该app注册到微信
        mWxApi.registerApp(Constants.WX.WEIXIN_APP_ID)
    }

    private fun initRequestBase() {
        RxHttp.INSTANCE
                .init(context = baseContext)
                .baseUrl("http://10.38.64.79:8088")
                .isLog(true)
                .readTimeout(60 * 1000)
                .writeTimeout(60 * 1000)
                .connectTimeout(60 * 1000)
                .retryCount(3)
                .retryDelay(500)
                .retryIncreaseDelay(500)
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
                .converterFactory(Retrofit2ConverterFactory())
    }


}