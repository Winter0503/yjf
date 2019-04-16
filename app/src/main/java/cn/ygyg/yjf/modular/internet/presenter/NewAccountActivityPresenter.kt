package cn.ygyg.yjf.modular.internet.presenter

import com.cn.lib.basic.BasePresenterImpl

import cn.ygyg.yjf.modular.internet.contract.NewAccountActivityContract

class NewAccountActivityPresenter(view: NewAccountActivityContract.View) :
        BasePresenterImpl<NewAccountActivityContract.View>(view),
        NewAccountActivityContract.Presenter {

}
