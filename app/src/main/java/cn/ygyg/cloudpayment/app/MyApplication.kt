package cn.ygyg.cloudpayment.app

import android.annotation.SuppressLint
import android.app.Application
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.api.ParamInterceptor
import cn.ygyg.cloudpayment.api.TokenInterceptor
import cn.ygyg.cloudpayment.utils.SharePreUtil
import cn.ygyg.cloudpayment.widget.LoadMoreView
import cn.ygyg.cloudpayment.widget.ProgressHeaderView
import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory
import com.cn.lib.retrofit.network.RxHttp
import com.cn.lib.util.ToastUtil
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


/**
 * Created by Admin on 2019/4/13.
 */
open class MyApplication : Application() {
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
        ToastUtil.initResId(R.mipmap.toast_success, 0)
        SharePreUtil.init(context = baseContext)
        TwinklingRefreshLayout.setDefaultHeader(ProgressHeaderView::class.java.name)
        TwinklingRefreshLayout.setDefaultFooter(LoadMoreView::class.java.name)
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
                .baseUrl("http://10.38.64.79:8088/chaoscloud-customerapi/")
//                .baseUrl("http://10.2.152.100:8130")
                .isLog(true)
                .readTimeout(60 * 1000)
                .writeTimeout(60 * 1000)
                .connectTimeout(60 * 1000)
                .retryCount(3)
                .retryDelay(500)
                .retryIncreaseDelay(500)
                .cancelEncryption(true)
                .addNetworkInterceptor(TokenInterceptor())
                .addInterceptor(ParamInterceptor())
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
                .converterFactory(Retrofit2ConverterFactory())
    }


}