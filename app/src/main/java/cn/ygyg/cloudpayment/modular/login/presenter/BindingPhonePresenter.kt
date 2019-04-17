package cn.ygyg.cloudpayment.modular.login.presenter

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import cn.ygyg.cloudpayment.modular.login.contract.BindingPhoneContract
import cn.ygyg.cloudpayment.utils.StringUtil
import com.cn.lib.basic.BasePresenterImpl
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BindingPhonePresenter(view: BindingPhoneContract.View) : BasePresenterImpl<BindingPhoneContract.View>(view), BindingPhoneContract.Presenter {
    private var isLegalPhone: Boolean = false
    private var isLegalCode: Boolean = false
    var disposable: Disposable? = null

    /**
     * 手机号码输入框过滤器
     */
    override fun getPhoneInputFilter(): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend ->
            var result: CharSequence? = null
            if (dstart == dend && !source.isEmpty()) { //当两者相等说明是输入
                if (dstart == 0 && "1" != source) { //输入的第一位必须是"1"，如果不是则不添加
                    isLegalPhone = false
                    result = ""
                }
                val count = end + dstart
                if (count == 11) { //当输入的长度达到11位时校验手机号
                    val str = "$dest$source"
                    if (StringUtil.checkCellPhone(str)) {
                        mvpView?.changeCodeBtnState(true)
                        isLegalPhone = true
                    } else {
                        isLegalPhone = false
                        mvpView?.showToast("请输入正确的手机号码")
                    }
                } else if (count > 11) { //已经输入11还在继续输入则不添加
                    result = ""
                }
            } else if (source.isEmpty() && dend > dstart) { //当dend大于dstart时说明时删除操作
                if (dend != dest.length && dstart == 0) { //如果是非尾端删除并且删除的是第一位
                    result = dest.subSequence(dstart, dend)
                } else {
                    disposable?.dispose()
                    mvpView?.changeCodeBtnState(false)
                    isLegalPhone = false
                }
            }
            checkAllInput()
            result
        }
    }

    override fun getCodeTextChangeListener(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                isLegalCode = s.length == 4
                checkAllInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
    }

    /**
     * 校验所有的输入时候符合规定，符合规定时设置按钮可点击
     */
    private fun checkAllInput() {
        mvpView?.changeConfirmBtnState(isLegalPhone && isLegalCode)
    }

    override fun getVerificationCode(phone: String) {
        startCountDown()
    }

    /**
     * 开始重新获取验证码的倒计时
     */
    private fun startCountDown() {
        val count = 60
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .take(count + 1L)
                .map { aLong -> count - aLong }
                .doOnSubscribe {
                    mvpView?.changeCodeBtnState(false)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(aLong: Long) {
                        mvpView?.changeCodeBtnText(aLong)
                    }

                    override fun onComplete() {
                        disposable?.dispose()
                        mvpView?.changeCodeBtnState(true)
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }
}