package cn.ygyg.cloudpayment.modular.login.activity

import android.text.InputFilter
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.dialog.DefaultPromptDialog
import cn.ygyg.cloudpayment.modular.login.contract.LoginContract
import cn.ygyg.cloudpayment.modular.login.presenter.LoginPresenter
import cn.ygyg.cloudpayment.modular.password.activity.ResetPasswordActivity
import cn.ygyg.cloudpayment.utils.ResourceUtil
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
        btn_login.setBackgroundResource(if (state) R.mipmap.btn_full_press else R.mipmap.btn_full_normal)
    }

    override fun changeCodeBtnState(state: Boolean) {
        btn_login_code.text = "获取验证码"
        btn_login_code.isEnabled = state
        btn_login_code.setTextColor(ResourceUtil.getColor(getViewContext(), if (state) R.color.text_green_color else R.color.text_gray_color))
    }

    override fun changeCodeBtnText(aLong: Long) {
        btn_login_code.text = "${aLong}秒后重发"
    }

    override fun getContentViewResId(): Int = R.layout.activity_login

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter(this)
    }

    override fun initViews() {
        super.initViews()
        edit_login_code.addTextChangedListener(mPresenter?.getPasswordTextChangeListener())
        edit_login_phone.filters = arrayOf(mPresenter?.getPhoneInputFilter())
    }

    override fun initListener() {
        super.initListener()
        btn_login_type.setOnClickListener {
            edit_login_code.text = null
            if (loginType == 0) { //判断是否是密码登录
                //验证码登录
                loginType = 1
                edit_login_code.addTextChangedListener(mPresenter?.getCodeTextChangeListener())
                edit_login_code.hint = ResourceUtil.getString(getViewContext(), R.string.verification_code)
                btn_retrieve_password.visibility = View.INVISIBLE
                btn_login_code.visibility = View.VISIBLE
                edit_login_code.filters = arrayOf(InputFilter.LengthFilter(4))
                btn_login_type.text ="密码登录"
            } else {
                //密码登录
                loginType = 0
                edit_login_code.addTextChangedListener(mPresenter?.getPasswordTextChangeListener())
                edit_login_code.hint = ResourceUtil.getString(getViewContext(), R.string.input_password)
                btn_retrieve_password.visibility = View.VISIBLE
                btn_login_code.visibility = View.GONE
                edit_login_code.filters = arrayOf(InputFilter.LengthFilter(20))
                btn_login_type.text ="验证码登录"
            }
            mPresenter?.setLoginType(loginType)
        }

        btn_retrieve_password.setOnClickListener { //找回密码
            DefaultPromptDialog.builder()
                    .setAffirmText("确认")
                    .setCancelText("取消")
                    .setContentText("找回密码")
                    .setContext(getViewContext())
                    .setButtonOrientation(typeEnum = DefaultPromptDialog.TypeEnum.BUTTON_HORIZONTAL)
                    .onPromptDialogButtonListener(object : DefaultPromptDialog.DefaultPromptDialogButtonListener() {
                        override fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean {
                                toActivity(ResetPasswordActivity::class.java)
                            return super.clickPositiveButton(dialog)
                        }
                    })
                    .build()
                    .show()
        }
        btn_login_code.setOnClickListener {
            mPresenter?.getVerificationCode(edit_login_phone.text.toString())
        }

    }

}