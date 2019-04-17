package cn.ygyg.yjf.modular.my.presenter

import cn.ygyg.yjf.modular.my.contract.MyContract
import com.cn.lib.basic.BasePresenterImpl

/**
 * Created by Admin on 2019/4/17.
 */
class MyPresenter(view: MyContract.View): BasePresenterImpl<MyContract.View>(view), MyContract.Presenter {
    override fun logout() {
        mvpView?.logoutSuccess()
    }
}