package cn.ygyg.yjf.modular.my.contract

import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

/**
 * Created by Admin on 2019/4/17.
 */
class MyContract {
    interface View : IBaseView {
        fun logoutSuccess()

    }

    interface Presenter : IBasePresenter<View> {
        /**
         * 退出登录
         */
        fun logout()

    }
}