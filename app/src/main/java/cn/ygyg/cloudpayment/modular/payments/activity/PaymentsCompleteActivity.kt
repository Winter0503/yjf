package cn.ygyg.cloudpayment.modular.payments.activity

import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import com.cn.lib.basic.BaseActivity
import kotlinx.android.synthetic.main.activity_payments_complete.*

class PaymentsCompleteActivity : BaseActivity() {
    override fun getContentViewResId(): Int = R.layout.activity_payments_complete

    override fun initViews() {
        var isSuccess = false
        bundle?.let {
            isSuccess = it.getBoolean(Constants.IntentKey.IS_SUCCESS)
        }
        val builder = HeaderBuilder(this)
        builder.setLeftImageRes(R.mipmap.back)
        if (isSuccess) {
            state_icon.setImageResource(R.mipmap.icon_success)
            state_name.setText(R.string.payment_success)
            to_payments_history.visibility = View.VISIBLE
            payments_amount.visibility = View.INVISIBLE
            payment_again.visibility = View.GONE
            builder.setTitle(R.string.payment_success)
        } else {
            state_icon.setImageResource(R.mipmap.icon_failed)
            state_name.setText(R.string.payment_failed)
            to_payments_history.visibility = View.GONE
            payments_amount.visibility = View.VISIBLE
            payment_again.visibility = View.VISIBLE
            builder.setTitle(R.string.payment_failed)

        }
        amount_num.text = "500.00"
    }
}
