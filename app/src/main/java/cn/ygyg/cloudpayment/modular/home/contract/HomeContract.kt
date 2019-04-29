package cn.ygyg.cloudpayment.modular.home.contract

import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

/**
 * Created by Admin on 2019/4/17.
 */
class HomeContract {
    interface View : IBaseView {
        fun loaderSuccess(response: ArrayList<out DeviceVM>?)
        /**
         * 解除绑定成功
         */
        fun unbindSuccess(position: Int, device: DeviceVM)

        fun loaderCompleted()
    }

    interface Presenter : IBasePresenter<View> {
        fun loaderData()
        /**
         * 解除绑定设备
         */
        fun unBindDevice(position: Int, device: DeviceVM)

    }
}