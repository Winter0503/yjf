package cn.ygyg.yjf.modular.password.activity

import android.annotation.SuppressLint
import android.text.InputType
import android.view.inputmethod.EditorInfo
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.password.contract.ResetPasswordContract
import cn.ygyg.yjf.modular.password.presenter.ResetPasswordPresenter
import cn.ygyg.yjf.utils.ResourceUtil
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity: BaseMvpActivity<ResetPasswordContract.Presenter, ResetPasswordContract.View>(),ResetPasswordContract.View {
    override fun changeConfirmBtnState(state: Boolean) {
        btn_confirm.isEnabled = state
        btn_confirm.setBackgroundResource(if(state)R.mipmap.btn_full_press else R.mipmap.btn_full_normal)
    }

    override fun changeCodeBtnState(state: Boolean) {
        btn_reset_code.text = "获取验证码"
        btn_reset_code.isEnabled = state
        btn_reset_code.setTextColor(ResourceUtil.getColor(getViewContext(), if (state) R.color.text_green_color else R.color.text_gray_color))
    }

    override fun createPresenter(): ResetPasswordContract.Presenter = ResetPasswordPresenter(this)

    override fun getContentViewResId(): Int = R.layout.activity_reset_password

    override fun initViews() {
        super.initViews()
        edit_reset_pwd.addTextChangedListener(mPresenter?.getPasswordTextChangeListener())
        edit_reset_code.addTextChangedListener(mPresenter?.getCodeTextChangeListener())
        edit_reset_phone.filters = arrayOf(mPresenter?.getPhoneInputFilter()) //添加手机号文本框的输入过滤器
    }

    override fun initListener() {
        super.initListener()
        btn_back.setOnClickListener {
            finish()
        }
        btn_pwd.setOnClickListener {
            if (edit_reset_pwd.inputType != EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) { //显示密码
                edit_reset_pwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btn_pwd.setImageResource(R.mipmap.pwd_open)
            } else {//隐藏密码
                edit_reset_pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btn_pwd.setImageResource(R.mipmap.pwd_close)
            }
        }
        btn_reset_code.setOnClickListener { //获取验证码
            mPresenter?.getVerificationCode(edit_reset_phone.text.toString())
        }
        btn_confirm.setOnClickListener {
            edit_reset_phone.text.toString()
            edit_reset_code.text.toString()
            edit_reset_pwd.text.toString()
        }

    }

    @SuppressLint("SetTextI18n")
    override fun changeCodeBtnText(aLong: Long) {
        btn_reset_code.text = "${aLong}秒后重发"
    }


}