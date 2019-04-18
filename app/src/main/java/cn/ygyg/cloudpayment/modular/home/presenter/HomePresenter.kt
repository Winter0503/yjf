package cn.ygyg.cloudpayment.modular.home.presenter

import cn.ygyg.cloudpayment.modular.home.contract.HomeContract
import com.cn.lib.basic.BasePresenterImpl

/**
 * Created by Admin on 2019/4/17.
 */
class HomePresenter(view: HomeContract.View) : BasePresenterImpl<HomeContract.View>(view), HomeContract.Presenter {
    override fun loaderData() {
        mvpView?.loaderSuccess(null)
    }
}