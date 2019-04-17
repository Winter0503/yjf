package cn.ygyg.cloudpayment.modular.payments.presenter

import cn.ygyg.cloudpayment.modular.payments.contract.PaymentsHistoryActivityContract
import com.cn.lib.basic.BasePresenterImpl

class PaymentsHistoryActivityPresenter(view: PaymentsHistoryActivityContract.View) :
        BasePresenterImpl<PaymentsHistoryActivityContract.View>(view),
        PaymentsHistoryActivityContract.Presenter {
    override fun loadPage(pageNum: Int, pageSize: Int) {

    }
}