package cn.ygyg.cloudpayment.modular.internet.presenter

import cn.ygyg.cloudpayment.api.RequestManager
import cn.ygyg.cloudpayment.api.UrlConstants
import cn.ygyg.cloudpayment.modular.internet.contract.NewAccountActivityContract
import cn.ygyg.cloudpayment.modular.internet.entity.DeviceResponseEntity
import cn.ygyg.cloudpayment.utils.ProgressUtil
import cn.ygyg.cloudpayment.utils.UserUtil
import com.cn.lib.basic.BasePresenterImpl
import com.cn.lib.retrofit.network.callback.ResultCallback
import com.cn.lib.retrofit.network.exception.ApiThrowable

class NewAccountActivityPresenter(view: NewAccountActivityContract.View) :
        BasePresenterImpl<NewAccountActivityContract.View>(view),
        NewAccountActivityContract.Presenter {
    override fun getDevice(deviceCode: String) {
        RequestManager.get(UrlConstants.getDevice)
                .param("meterCode", deviceCode)
                .param("username", UserUtil.getUserName())
                .execute("", object : ResultCallback<DeviceResponseEntity>() {
                    override fun onStart(tag: Any?) {
                        mvpView?.getViewContext()?.let {
                            ProgressUtil.showProgressDialog(it, "加载中...")
                        }
                    }

                    override fun onCompleted(tag: Any?) {
                        mvpView?.getViewContext()?.let {
                            ProgressUtil.dismissProgressDialog()
                        }
                    }

                    override fun onError(tag: Any?, e: ApiThrowable) {
                        e.message?.let { mvpView?.showToast(it) }
                    }

                    override fun onSuccess(tag: Any?, result: DeviceResponseEntity?) {
                        result?.let {
                            mvpView?.onLoadDeviceSuccess(it)
                        }
                    }
                })
    }

    override fun bindDevice(deviceCode: String, companyCode: String) {
        RequestManager.post(UrlConstants.bindDevice)
                .param("meterCode", deviceCode)
                .param("username", UserUtil.getUserName())
                .execute("", object : ResultCallback<String>() {
                    override fun onStart(tag: Any?) {
                        mvpView?.getViewContext()?.let {
                            ProgressUtil.showProgressDialog(it, "绑定中...")
                        }
                    }

                    override fun onCompleted(tag: Any?) {
                        mvpView?.getViewContext()?.let {
                            ProgressUtil.dismissProgressDialog()
                        }
                    }

                    override fun onError(tag: Any?, e: ApiThrowable) {
                        e.message?.let { mvpView?.showToast(it) }
                    }

                    override fun onSuccess(tag: Any?, result: String?) {
                        mvpView?.onBindDeviceSuccess(deviceCode, companyCode)
                    }
                })
    }
}
