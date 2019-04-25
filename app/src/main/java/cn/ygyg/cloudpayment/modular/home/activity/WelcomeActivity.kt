package cn.ygyg.cloudpayment.modular.home.activity

import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.app.Constants.IntentKey.IS_LOGIN
import cn.ygyg.cloudpayment.modular.login.activity.LoginActivity
import cn.ygyg.cloudpayment.utils.SharePreUtil
import com.cn.lib.basic.BaseActivity

class WelcomeActivity : BaseActivity() {
    override fun getContentViewResId(): Int = R.layout.activity_welcome

    override fun initViews() {
        super.initViews()
        val isLogin = SharePreUtil.getBoolean(IS_LOGIN)
        if (isLogin) {
            toActivity(MainTabActivity::class.java)
        }else{
            toActivity(LoginActivity::class.java)
        }
        this.finish()
    }
}