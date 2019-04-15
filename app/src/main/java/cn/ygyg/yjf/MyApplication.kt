package cn.ygyg.yjf

import android.app.Application
import com.cn.lib.retrofit.network.RxHttp

/**
 * Created by Admin on 2019/4/13.
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RxHttp.INSTANCE
                .init(context = baseContext)
                .baseUrl("http://www.baidu.com")
                .isLog(true)
                .readTimeout(60 * 1000)
                .writeTimeout(60 * 1000)
                .connectTimeout(60 * 1000)
                .retryCount(3)
                .retryDelay(500)
                .retryIncreaseDelay(500)
    }
}