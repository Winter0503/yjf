package cn.ygyg.cloudpayment.modular.my.presenter

import cn.ygyg.cloudpayment.app.Constants.IntentKey.USER_INFO
import cn.ygyg.cloudpayment.modular.login.entity.LoginEntity
import cn.ygyg.cloudpayment.modular.my.contract.MyContract
import cn.ygyg.cloudpayment.utils.SharePreUtil
import com.cn.lib.basic.BasePresenterImpl

/**
 * Created by Admin on 2019/4/17.
 */
class MyPresenter(view: MyContract.View): BasePresenterImpl<MyContract.View>(view), MyContract.Presenter {
    override fun loaderPageData() {
        val entity = SharePreUtil.getBeanByFastJson(USER_INFO, LoginEntity::class.java)
        mvpView?.loaderPageDataSuccess(entity)
    }

    override fun logout() {
        mvpView?.logoutSuccess()
    }
}