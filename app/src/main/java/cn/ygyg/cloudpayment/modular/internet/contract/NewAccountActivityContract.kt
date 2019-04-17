package cn.ygyg.cloudpayment.modular.internet.contract

import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

class NewAccountActivityContract {
    interface View : IBaseView

    interface Presenter : IBasePresenter<View>
}
