package cn.ygyg.cloudpayment.modular.home.presenter

import android.content.Context
import cn.ygyg.cloudpayment.BuildConfig
import cn.ygyg.cloudpayment.api.RequestManager
import cn.ygyg.cloudpayment.api.UrlConstants
import cn.ygyg.cloudpayment.modular.home.contract.WelcomeContract
import cn.ygyg.cloudpayment.utils.AppUtil
import cn.ygyg.cloudpayment.utils.ConfigUtil
import com.cn.lib.basic.BasePresenterImpl
import com.cn.lib.retrofit.network.callback.ResultCallback
import com.cn.lib.retrofit.network.exception.ApiThrowable

class WelcomePresenter(view: WelcomeContract.View) : BasePresenterImpl<WelcomeContract.View>(view), WelcomeContract.Presenter {
    /**
     * 加载厂家配置
     */
    override fun loaderPaymentConfig(context: Context) {
        RequestManager.get(UrlConstants.getPaymentInformation)
                .param("configurationName", BuildConfig.APPLICATION_ID)
                .execute("getCompany", object : ResultCallback<ConfigUtil.ConfigEntity>() {

                    override fun onStart(tag: Any?) {

                    }

                    override fun onCompleted(tag: Any?) {
                        mvpView?.completed()
                    }

                    override fun onError(tag: Any?, e: ApiThrowable) {
                        mvpView?.loaderConfigError()
                    }

                    override fun onSuccess(tag: Any?, result: ConfigUtil.ConfigEntity?) {
                        result?.apply {
                            ConfigUtil.saveConfig(this)
                            if (ConfigUtil.isNotEmpty()) {
                                mvpView?.jumpPage()
                            } else {
                                mvpView?.loaderConfigError()
                            }
                        } ?: mvpView?.loaderConfigError()
                    }
                })
    }
}