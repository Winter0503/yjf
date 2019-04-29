package cn.ygyg.cloudpayment.modular.payments.presenter

import cn.ygyg.cloudpayment.api.RequestManager
import cn.ygyg.cloudpayment.api.UrlConstants
import cn.ygyg.cloudpayment.modular.internet.entity.DeviceResponseEntity
import com.cn.lib.basic.BasePresenterImpl

import cn.ygyg.cloudpayment.modular.payments.contract.PaymentsActivityContract
import cn.ygyg.cloudpayment.modular.payments.entity.CreateOrderResposneEntity
import cn.ygyg.cloudpayment.utils.ProgressUtil
import cn.ygyg.cloudpayment.utils.UserUtil
import com.cn.lib.retrofit.network.callback.ResultCallback
import com.cn.lib.retrofit.network.exception.ApiThrowable

class PaymentsActivityPresenter(view: PaymentsActivityContract.View) :
        BasePresenterImpl<PaymentsActivityContract.View>(view),
        PaymentsActivityContract.Presenter {
    override fun getBindDevice(deviceCode: String, companyCode: String) {
        RequestManager.post(UrlConstants.getDevice)
                .param("meterCode", deviceCode)
                .param("companyCode", companyCode)
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

    override fun createOrder(amount: String, deviceCode: String, phone: String, payMode: String, payType: String) {
        RequestManager.post(UrlConstants.createOrder)
                .param("amount", amount)
                .param("contractCode", deviceCode)
                .param("mobile", phone)
                .param("paymentMethod", payMode)
                .param("paymentType", payType)
                .execute("", object : ResultCallback<CreateOrderResposneEntity>() {
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

                    override fun onSuccess(tag: Any?, result: CreateOrderResposneEntity?) {
                        result?.let {
                            mvpView?.onCreateOrderSuccess()
                        }
                    }
                })
    }

}
