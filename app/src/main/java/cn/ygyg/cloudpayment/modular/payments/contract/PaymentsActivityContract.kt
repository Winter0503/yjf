package cn.ygyg.cloudpayment.modular.payments.contract

import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

class PaymentsActivityContract {
    interface View : IBaseView {
        fun onLoadDeviceSuccess(response: DeviceVM)
    }

    interface Presenter : IBasePresenter<View> {
        fun getBindDevice(deviceCode: String)
    }
}
