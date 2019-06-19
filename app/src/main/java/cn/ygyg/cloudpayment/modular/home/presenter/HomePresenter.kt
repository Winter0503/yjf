package cn.ygyg.cloudpayment.modular.home.presenter

import cn.ygyg.cloudpayment.net.RequestManager
import cn.ygyg.cloudpayment.net.UrlConstants
import cn.ygyg.cloudpayment.modular.home.contract.HomeContract
import cn.ygyg.cloudpayment.modular.home.entity.DeviceListResponseEntity
import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import cn.ygyg.cloudpayment.utils.ConfigUtil
import cn.ygyg.cloudpayment.utils.ProgressUtil
import cn.ygyg.cloudpayment.utils.UserUtil
import com.cn.lib.basic.BasePresenterImpl
import com.cn.lib.retrofit.network.callback.ResultCallback
import com.cn.lib.retrofit.network.exception.ApiThrowable

/**
 * Created by Admin on 2019/4/17.
 */
class HomePresenter(view: HomeContract.View) : BasePresenterImpl<HomeContract.View>(view), HomeContract.Presenter {

    override fun loaderData() {
        RequestManager.post(UrlConstants.deviceList)
                .param("companyCode", ConfigUtil.getCompanyCode())
                .param("username", UserUtil.getUserName())
                .execute("", object : ResultCallback<DeviceListResponseEntity>() {
                    override fun onStart(tag: Any?) {

                    }

                    override fun onCompleted(tag: Any?) {
                        mvpView?.loaderCompleted()
                    }

                    override fun onError(tag: Any?, e: ApiThrowable) {
                        e.message?.let { mvpView?.showToast(it) }
                    }

                    override fun onSuccess(tag: Any?, result: DeviceListResponseEntity?) {
                        mvpView?.loaderSuccess(result?.list)
                    }

                })
    }

    override fun unBindDevice(position: Int, device: DeviceVM) {
        RequestManager.post(UrlConstants.unbind)
                .param("userName", UserUtil.getUserName())
                .param("companyCode", ConfigUtil.getCompanyCode())
                .param("meterCode", device.deviceCode())
                .execute("", object : ResultCallback<String>() {
                    override fun onStart(tag: Any?) {
                        mvpView?.let {
                            ProgressUtil.showProgressDialog(it.getViewContext(), "加载中...")
                        }
                    }

                    override fun onCompleted(tag: Any?) {
                        mvpView?.let {
                            ProgressUtil.dismissProgressDialog()
                        }
                    }

                    override fun onError(tag: Any?, e: ApiThrowable) {
                        e.message?.let { mvpView?.showToast(it) }
                    }

                    override fun onSuccess(tag: Any?, result: String?) {
                        mvpView?.unbindSuccess(position, device)
                    }
                })
    }
}