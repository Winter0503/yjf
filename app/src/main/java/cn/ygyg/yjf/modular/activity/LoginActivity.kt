package cn.ygyg.yjf.modular.activity

import android.view.View
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.contract.LoginContract
import cn.ygyg.yjf.modular.presenter.LoginPresenter
import cn.ygyg.yjf.utils.ResourceUtil
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录
 * Created by Admin on 2019/4/13.
 */
class LoginActivity : BaseMvpActivity<LoginContract.Presenter, LoginContract.View>(), LoginContract.View {
    private var loginType: Int = 0

    override fun changeLoginBtnState(state: Boolean) {
        btn_login.isEnabled = state
        btn_login.setBackgroundResource(if (state) R.mipmap.btn_selected_long else R.mipmap.btn_unselected_long)
    }

    override fun changeCodeBtnState(state: Boolean) {
        if (state) btn_code.text = "获取验证码"
        btn_code.isEnabled = state
        btn_code.setTextColor(ResourceUtil.getColor(getViewContext(), if (state) R.color.text_green_color else R.color.text_gray_color))
    }

    override fun changeCodeBtnText(aLong: Long) {
        btn_code.text = "${aLong}秒后重发"
    }

    override fun getContentViewResId(): Int = R.layout.activity_login

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter(this)
    }

    override fun initViews() {
        super.initViews()
    }

    override fun initListener() {
        super.initListener()
        edit_code.addTextChangedListener(mPresenter?.getPasswordTextChangeListener())
        btn_login_type.setOnClickListener {
            if (loginType == 0) { //判断是否是密码登录
                //验证码登录
                loginType = 1
                edit_code.addTextChangedListener(mPresenter?.getCodeTextChangeListener())
                edit_code.hint = "请输入验证码"
                btn_retrieve_password.visibility = View.INVISIBLE
                btn_code.visibility = View.VISIBLE
                btn_code
            } else {
                //密码登录
                loginType = 0
                edit_code.addTextChangedListener(mPresenter?.getPasswordTextChangeListener())
                edit_code.hint = "请输入密码"
                btn_retrieve_password.visibility = View.VISIBLE
                btn_code.visibility = View.GONE
            }
            mPresenter?.setLoginType(loginType)
        }
        edit_phone.filters = arrayOf(mPresenter?.getPhoneInputFilter())
        btn_retrieve_password.setOnClickListener { //找回密码

        }

    }

}