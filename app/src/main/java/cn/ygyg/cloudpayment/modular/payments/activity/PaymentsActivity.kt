package cn.ygyg.cloudpayment.modular.payments.activity

import android.widget.CompoundButton
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.payments.contract.PaymentsActivityContract
import cn.ygyg.cloudpayment.modular.payments.presenter.PaymentsActivityPresenter
import cn.ygyg.cloudpayment.utils.DecimalDigitsInputFilter
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_payments.*

class PaymentsActivity :
        BaseMvpActivity<PaymentsActivityContract.Presenter, PaymentsActivityContract.View>(),
        PaymentsActivityContract.View, CompoundButton.OnCheckedChangeListener {
    override fun createPresenter(): PaymentsActivityContract.Presenter = PaymentsActivityPresenter(this)

    override fun getContentViewResId(): Int = R.layout.activity_payments
    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_payments)
            setLeftImageRes(R.mipmap.back)
        }

        input_amount.filters = arrayOf(DecimalDigitsInputFilter(2))
    }

    override fun initListener() {
        payments_history.setOnClickListener { toActivity(PaymentsHistoryActivity::class.java) }
        payments.setOnClickListener { toActivity(PaymentsCompleteActivity::class.java) }
        selector_rmb100.setOnCheckedChangeListener(this)
        selector_rmb300.setOnCheckedChangeListener(this)
        selector_rmb800.setOnCheckedChangeListener(this)
        input_amount.setOnFocusChangeListener { v, hasFocus ->
            v.isSelected = hasFocus
            onCheckedChanged(null, false)
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        selector_rmb100.isChecked = buttonView == selector_rmb100
        selector_rmb300.isChecked = buttonView == selector_rmb300
        selector_rmb800.isChecked = buttonView == selector_rmb800
    }
}
