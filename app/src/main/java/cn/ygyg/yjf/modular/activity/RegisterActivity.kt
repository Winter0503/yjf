package cn.ygyg.yjf.modular.activity

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import cn.ygyg.yjf.R
import cn.ygyg.yjf.R.id.*
import cn.ygyg.yjf.modular.contract.RegisterContract
import cn.ygyg.yjf.modular.presenter.RegisterPresenter
import cn.ygyg.yjf.utils.ResourceUtil
import cn.ygyg.yjf.utils.StringUtil
import com.cn.lib.basic.BaseMvpActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import java.util.concurrent.TimeUnit


/**
 * 注册
 * Created by Admin on 2019/4/12.
 */
class RegisterActivity : BaseMvpActivity<RegisterContract.Presenter, RegisterContract.View>(), RegisterContract.View {

    private var phoneRight = false
    private var passwordRight = false
    private var codeRight = false

    override fun getContentViewResId(): Int = R.layout.activity_register

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter(this)

    override fun initViews() {
        super.initViews()
    }

    override fun initListener() {
        super.initListener()
        btn_back.setOnClickListener {
            this.finish()
        }
        btn_pwd.setOnClickListener {
            if (edit_pwd.inputType != EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) { //显示密码
                edit_pwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btn_pwd.setImageResource(R.mipmap.pwd_open)
            } else {//隐藏密码
                edit_pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btn_pwd.setImageResource(R.mipmap.pwd_close)
            }
        }
        edit_phone.filters = arrayOf(getPhoneInputFilter())
        btn_code.setOnClickListener {
            mPresenter?.checkPhone(edit_phone.text.toString())
        }
        btn_agreement.setOnClickListener {
            toActivity(UserAgreementActivity::class.java)
        }
        edit_pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                passwordRight = false
                val length = s.length
                if (length >= 6) {
                    val reg = "^(?![a-zA-Z]+\$)(?!\\d+\$)\\S{6,}\$"
                    if (!StringUtil.match(reg, s.toString())) {
                        showToast("密码设置6-20位必须包含数字和字母")
                    } else { //当密码输入符合设定时进行校验
                        passwordRight = true
                    }
                }
                checkAllInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        edit_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                codeRight = s.length==4
                checkAllInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    override fun checkPhoneSuccess() {
        startCountDown()
    }

    private fun checkAllInput(){
        if (phoneRight && codeRight && passwordRight){
            btn_register.isEnabled = true
            btn_register.setBackgroundResource(R.mipmap.btn_selected_long)
        }else{
            btn_register.isEnabled = false
            btn_register.setBackgroundResource(R.mipmap.btn_unselected_long)
        }
    }

    private fun getPhoneInputFilter(): InputFilter {
        return InputFilter { source, start, end, dest, dStart, dend ->
            var result:String? = null
            if (dStart == 0 && "1" != source) {
                phoneRight = false
                result = ""
            }
            val count = end + dStart
            if (count == 11) { //当输入的长度达到11位时校验手机号
                val str = "$dest$source"
                if (StringUtil.checkCellPhone(str)) {
                    btn_code.isEnabled = true
                    btn_code.setTextColor(ResourceUtil.getColor(getViewContext(), R.color.text_green_color))
                    phoneRight = true
                } else {
                    phoneRight = false
                    showToast("请输入正确的手机号码")
                }
            } else if (count > 11) { //限定不能超过11位
                result = ""
            }else if(source.isEmpty() && dend > dStart){ //删除操作

            }
            Log.e("TAG","======phoneRight=======$phoneRight")
            Log.e("TAG","======source:${source} end:${end} dest:${dest} dStart:${dStart} dend:${dend} source:${source} ")
            checkAllInput()
            result
        }
    }

    private fun startCountDown() {
        var disposable: Disposable? = null
        val count = 60
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .take(count + 1L)
                .map { aLong -> count - aLong }
                .doOnSubscribe {
                    btn_code.isEnabled = false
                    btn_code.setTextColor(ResourceUtil.getColor(getViewContext(), R.color.text_gray_color))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onNext(aLong: Long) {
                        btn_code.text = "${aLong}秒后重发"
                    }

                    override fun onComplete() {
                        disposable?.dispose()
                        btn_code.text = "获取验证码"
                        btn_code.isEnabled = true
                        btn_code.setTextColor(ResourceUtil.getColor(getViewContext(), R.color.text_green_color))
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

}