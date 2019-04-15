package cn.ygyg.yjf.modular.internet.presenter

import cn.ygyg.yjf.modular.internet.contract.AddressSelectorActivityContract
import com.cn.lib.basic.BasePresenterImpl

class AddressSelectorActivityPresenter(view: AddressSelectorActivityContract.View) :
        BasePresenterImpl<AddressSelectorActivityContract.View>(view),
        AddressSelectorActivityContract.Presenter {

}
