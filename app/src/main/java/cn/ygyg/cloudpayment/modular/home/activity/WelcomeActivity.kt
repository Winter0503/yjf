package cn.ygyg.cloudpayment.modular.home.activity

import android.annotation.SuppressLint
import android.text.TextUtils
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.login.activity.BindingPhoneActivity
import cn.ygyg.cloudpayment.modular.login.activity.LoginActivity
import cn.ygyg.cloudpayment.utils.UserUtil
import com.cn.lib.basic.BaseActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class WelcomeActivity : BaseActivity() {
    override fun getContentViewResId(): Int = R.layout.activity_welcome

    @SuppressLint("CheckResult")
    override fun initViews() {
        super.initViews()
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(4)
                .subscribe { t ->
                    if (t == 3L) {
                        when {
                            UserUtil.isLogin() -> toActivity(MainTabActivity::class.java)
                            !TextUtils.isEmpty(UserUtil.getToken()) -> toActivity(BindingPhoneActivity::class.java)
                            else -> toActivity(LoginActivity::class.java)
                        }
                        this.finish()
                    }
                }
    }
}