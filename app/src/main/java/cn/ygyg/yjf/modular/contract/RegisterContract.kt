package cn.ygyg.yjf.modular.contract

import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

/**
 * Created by Admin on 2019/4/12.
 */
class RegisterContract {
    interface View : IBaseView {
        fun checkPhoneSuccess()
    }

    interface Presenter : IBasePresenter<View> {
        fun checkPhone(phone: String)
    }
}