package cn.ygyg.yjf.modular.activity

import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.contract.LoginContract
import cn.ygyg.yjf.modular.presenter.LoginPresenter
import com.cn.lib.basic.BaseMvpActivity

/**
 * 登录
 * Created by Admin on 2019/4/13.
 */
class LoginActivity : BaseMvpActivity<LoginContract.Presenter, LoginContract.View>(), LoginContract.View {

    override fun getContentViewResId(): Int = R.layout.activity_login

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter(this)
    }

}