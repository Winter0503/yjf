package cn.ygyg.cloudpayment.modular.internet.contract

import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

class NewAccountActivityContract {
    interface View : IBaseView {
        /**
         * 查询 物联网表数据
         */
        fun onLoadDeviceSuccess(result: DeviceVM)

        /**
         * 绑定 物联网表
         */
        fun onBindDeviceSuccess(deviceCode: String, companyCode: String)
    }

    interface Presenter : IBasePresenter<View> {
        /**
         * 获取物联网表 信息
         */
        fun getDevice(deviceCode: String)

        /**
         * 绑定物联网表
         */
        fun bindDevice(deviceCode: String,companyCode:String)
    }
}
