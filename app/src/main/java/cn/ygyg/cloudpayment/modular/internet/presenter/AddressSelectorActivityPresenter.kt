package cn.ygyg.cloudpayment.modular.internet.presenter

import cn.ygyg.cloudpayment.modular.internet.contract.AddressSelectorActivityContract
import com.cn.lib.basic.BasePresenterImpl

class AddressSelectorActivityPresenter(view: AddressSelectorActivityContract.View) :
        BasePresenterImpl<AddressSelectorActivityContract.View>(view),
        AddressSelectorActivityContract.Presenter {

}
