package cn.ygyg.cloudpayment.modular.home.contract

import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

/**
 * Created by Admin on 2019/4/17.
 */
class HomeContract {
    interface View:IBaseView{
        fun loaderSuccess(mutableList: MutableList<String>?)
    }

    interface Presenter:IBasePresenter<View>{
        fun loaderData()

    }
}