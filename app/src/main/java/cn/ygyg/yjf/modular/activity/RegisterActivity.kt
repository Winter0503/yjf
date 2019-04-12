package cn.ygyg.yjf.modular.activity

import android.annotation.SuppressLint
import android.text.*
import android.util.Log
import android.view.inputmethod.EditorInfo
import cn.ygyg.yjf.R

import cn.ygyg.yjf.modular.contract.RegisterContract
import cn.ygyg.yjf.modular.presenter.RegisterPresenter
import cn.ygyg.yjf.util.ResourceUtil
import cn.ygyg.yjf.util.StringUtil
import com.cn.lib.basic.BaseMvpActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import java.util.concurrent.TimeUnit


/**
 * 注册
 * Created by Admin on 2019/4/12.
 */
class RegisterActivity : BaseMvpActivity<RegisterContract.Presenter, RegisterContract.View>(), RegisterContract.View {

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
    }

    override fun checkPhoneSuccess() {
        startCountDown()
    }

    private fun getPhoneInputFilter(): InputFilter {
        return InputFilter { source, _, end, dest, dStart, _ ->
            if (dStart == 0 && "1" != source) {
                return@InputFilter ""
            }
            val count = end + dStart
            if (count == 11) {
                val str = "$dest$source"
                if (StringUtil.checkCellPhone(str)) {
                    btn_code.isEnabled = true
                    btn_code.setTextColor(ResourceUtil.getColor(getViewContext(), R.color.text_green_color))
                } else {
                    showToast("请输入正确的手机号码")
                }
            } else if (count > 11) {
                return@InputFilter ""
            }
            null
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