package cn.ygyg.yjf.modular.login.activity

import android.annotation.SuppressLint
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.login.contract.BindingPhoneContract
import cn.ygyg.yjf.modular.login.presenter.BindingPhonePresenter
import cn.ygyg.yjf.utils.ResourceUtil
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_binding_phone.*


class BindingPhoneActivity: BaseMvpActivity<BindingPhoneContract.Presenter, BindingPhoneContract.View>(),BindingPhoneContract.View {
    override fun changeConfirmBtnState(state: Boolean) {
        btn_binding_confirm.isEnabled = state
        btn_binding_confirm.setBackgroundResource(if(state)R.mipmap.btn_selected_long else R.mipmap.btn_unselected_long)
    }

    override fun changeCodeBtnState(state: Boolean) {
        btn_binding_code.text = "获取验证码"
        btn_binding_code.isEnabled = state
        btn_binding_code.setTextColor(ResourceUtil.getColor(getViewContext(), if (state) R.color.text_green_color else R.color.text_gray_color))
    }

    override fun createPresenter(): BindingPhoneContract.Presenter = BindingPhonePresenter(this)

    override fun getContentViewResId(): Int = R.layout.activity_binding_phone

    override fun initViews() {
        super.initViews()
        edit_binding_phone.filters = arrayOf(mPresenter?.getPhoneInputFilter()) //添加手机号文本框的输入过滤器
        edit_binding_code.addTextChangedListener(mPresenter?.getCodeTextChangeListener())
    }

    override fun initListener() {
        super.initListener()
        btn_back.setOnClickListener {
            finish()
        }
        btn_binding_code.setOnClickListener { //获取验证码
            mPresenter?.getVerificationCode(edit_binding_phone.text.toString())
        }
        btn_binding_confirm.setOnClickListener {
            edit_binding_phone.text.toString()
            edit_binding_code.text.toString()
        }

    }

    @SuppressLint("SetTextI18n")
    override fun changeCodeBtnText(aLong: Long) {
        btn_binding_code.text = "${aLong}秒后重发"
    }


}