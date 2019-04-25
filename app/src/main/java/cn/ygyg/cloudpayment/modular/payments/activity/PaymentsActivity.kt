package cn.ygyg.cloudpayment.modular.payments.activity

import android.content.Intent
import android.net.Uri
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.dialog.DefaultPromptDialog
import cn.ygyg.cloudpayment.modular.internet.entity.DeviceResponseEntity
import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import cn.ygyg.cloudpayment.modular.payments.contract.PaymentsActivityContract
import cn.ygyg.cloudpayment.modular.payments.presenter.PaymentsActivityPresenter
import cn.ygyg.cloudpayment.utils.DecimalDigitsInputFilter
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import cn.ygyg.cloudpayment.utils.ViewUtils
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_payments.*

class PaymentsActivity :
        BaseMvpActivity<PaymentsActivityContract.Presenter, PaymentsActivityContract.View>(),
        PaymentsActivityContract.View {
    override fun createPresenter(): PaymentsActivityContract.Presenter = PaymentsActivityPresenter(this)

    override fun getContentViewResId(): Int = R.layout.activity_payments
    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_payments)
            setLeftImageRes(R.mipmap.back)
        }
        input_amount.clearFocus()
        bundle?.let {
            val deviceCode = it.getString(Constants.IntentKey.DEVICE_CODE, "")
            mPresenter?.getBindDevice(deviceCode)
        }
    }

    override fun initListener() {
        payments_history.setOnClickListener { toActivity(PaymentsHistoryActivity::class.java) }
        payments.setOnClickListener { toActivity(PaymentsCompleteActivity::class.java) }

        val singleChose = View.OnClickListener { v ->
            if (v != null) {
                input_amount.setText("")
                ViewUtils.hideKeyboard(input_amount)
            }
            selector_rmb100.isChecked = selector_rmb100 == v
            selector_rmb300.isChecked = selector_rmb300 == v
            selector_rmb800.isChecked = selector_rmb800 == v
        }
        selector_rmb100.setOnClickListener(singleChose)
        selector_rmb300.setOnClickListener(singleChose)
        selector_rmb800.setOnClickListener(singleChose)
        input_amount.setOnFocusChangeListener { _, hasFocus ->
            input_layout.isSelected = hasFocus
            if (hasFocus) {
                singleChose.onClick(null)
            }
        }
        contact_service.setOnClickListener {
            DefaultPromptDialog.builder()
                    .setContext(getViewContext())
                    .setAffirmText("呼叫")
                    .setCancelText("取消")
                    .setContentText("0317-20725341")
                    .onPromptDialogButtonListener(object : DefaultPromptDialog.DefaultPromptDialogButtonListener() {
                        override fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean {
                            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0317-20725341"))
                            startActivity(dialIntent)
                            return super.clickPositiveButton(dialog)
                        }
                    })
                    .build()
                    .show()
        }

        ali_pay.setOnClickListener {
            wx_pay_select.visibility = View.GONE
            ali_pay_select.visibility = View.VISIBLE
        }
        wx_pay.setOnClickListener {
            ali_pay_select.visibility = View.GONE
            wx_pay_select.visibility = View.VISIBLE

        }
    }

    override fun onLoadDeviceSuccess(response: DeviceVM) {
        user_name.text = response.userName()
        user_account.text = response.deviceCode()
        user_name.text = response.deviceAddress()
        user_name.text = response.deviceBalance()
    }
}
