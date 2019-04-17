package cn.ygyg.cloudpayment.modular.payments.presenter

import com.cn.lib.basic.BasePresenterImpl

import cn.ygyg.cloudpayment.modular.payments.contract.PaymentsActivityContract

class PaymentsActivityPresenter(view: PaymentsActivityContract.View) :
        BasePresenterImpl<PaymentsActivityContract.View>(view),
        PaymentsActivityContract.Presenter {

}
