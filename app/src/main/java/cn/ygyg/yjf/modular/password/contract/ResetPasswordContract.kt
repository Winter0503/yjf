package cn.ygyg.yjf.modular.password.contract

import android.text.InputFilter
import android.text.TextWatcher
import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

class ResetPasswordContract {
    interface View:IBaseView{
        fun changeCodeBtnState(state: Boolean)
        fun changeConfirmBtnState(state: Boolean)
        fun changeCodeBtnText(aLong: Long)

    }

    interface Presenter:IBasePresenter<View>{
        fun getPhoneInputFilter(): InputFilter?
        fun getVerificationCode(phone: String)

        fun getCodeTextChangeListener(): TextWatcher
        fun getPasswordTextChangeListener(): TextWatcher
    }


}
