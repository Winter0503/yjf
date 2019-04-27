package cn.ygyg.cloudpayment.modular.payments.contract

import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

class PaymentsActivityContract {
    interface View : IBaseView {
        fun onLoadDeviceSuccess(response: DeviceVM)
        fun onCreateOrderSuccess()
    }

    interface Presenter : IBasePresenter<View> {
        fun getBindDevice(deviceCode: String)
        /**
         * 创建支付订单
         * @param amount 支付金额
         * @param deviceCode 物联网表编号
         * @param phone 手机号/账户名
         * @param payMode 支付方式
         * @param payType 泛起支付的平台类型 APP
         *
         */
        fun createOrder(amount: String, deviceCode: String, phone: String, payMode: String, payType: String)
    }
}
