package cn.ygyg.yjf.modular.presenter

import cn.ygyg.yjf.modular.contract.LoginContract
import com.cn.lib.basic.BasePresenterImpl

/**
 * Created by Admin on 2019/4/13.
 */
class LoginPresenter(view: LoginContract.View) : BasePresenterImpl<LoginContract.View>(view), LoginContract.Presenter {
}