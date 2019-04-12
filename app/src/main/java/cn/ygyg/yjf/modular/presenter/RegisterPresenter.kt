package cn.ygyg.yjf.modular.presenter

import cn.ygyg.yjf.modular.contract.RegisterContract
import com.cn.lib.basic.BasePresenterImpl

/**
 * Created by Admin on 2019/4/12.
 */
class RegisterPresenter(view:RegisterContract.View): RegisterContract.Presenter, BasePresenterImpl<RegisterContract.View>(view) {

    override fun checkPhone(phone: String) {
        mvpView?.checkPhoneSuccess()
    }

}