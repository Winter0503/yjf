package cn.ygyg.yjf.modular.payments.contract

import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

class PaymentsHistoryActivityContract {
    interface View : IBaseView

    interface Presenter : IBasePresenter<View>
}