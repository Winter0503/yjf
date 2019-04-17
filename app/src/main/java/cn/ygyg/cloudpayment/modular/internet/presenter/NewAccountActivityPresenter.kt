package cn.ygyg.cloudpayment.modular.internet.presenter

import com.cn.lib.basic.BasePresenterImpl

import cn.ygyg.cloudpayment.modular.internet.contract.NewAccountActivityContract

class NewAccountActivityPresenter(view: NewAccountActivityContract.View) :
        BasePresenterImpl<NewAccountActivityContract.View>(view),
        NewAccountActivityContract.Presenter {

}
