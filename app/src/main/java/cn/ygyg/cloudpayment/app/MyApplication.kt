package cn.ygyg.cloudpayment.app

import android.app.Application
import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory
import com.cn.lib.retrofit.network.RxHttp
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
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