package cn.ygyg.cloudpayment.modular.internet.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.dialog.DefaultPromptDialog
import cn.ygyg.cloudpayment.modular.internet.contract.NewAccountActivityContract
import cn.ygyg.cloudpayment.modular.internet.helper.ConfirmAccountDialog
import cn.ygyg.cloudpayment.modular.internet.helper.InquireAccountDialog
import cn.ygyg.cloudpayment.modular.internet.presenter.NewAccountActivityPresenter
import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import cn.ygyg.cloudpayment.modular.register.activity.UserAgreementActivity
import cn.ygyg.cloudpayment.utils.ConfigUtil
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import com.cn.lib.basic.BaseMvpActivity
import com.cn.lib.retrofit.network.exception.ApiThrowable
import kotlinx.android.synthetic.main.activity_new_account.*

class NewAccountActivity : BaseMvpActivity<NewAccountActivityContract.Presenter, NewAccountActivityContract.View>(),
        NewAccountActivityContract.View {
    private val headerBuilder: HeaderBuilder by lazy { HeaderBuilder(this) }
    private var deviceCode = ""
    private val companyCode = ConfigUtil.getCompanyCode()
    private val accountDialog: ConfirmAccountDialog by lazy {
        ConfirmAccountDialog(this).apply {
            setOnConformClick(View.OnClickListener {
                mPresenter?.bindDevice(deviceCode, companyCode)
            })
        }
    }
    private val dialog: InquireAccountDialog by lazy { InquireAccountDialog(this) }

    override fun createPresenter(): NewAccountActivityContract.Presenter {
        return NewAccountActivityPresenter(this)
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_new_account
    }

    override fun initViews() {
        headerBuilder.setLeftImageRes(R.mipmap.back)
        headerBuilder.setTitle(R.string.activity_title_add_new_account)
        pay_account.requestFocus()
    }

    override fun initListener() {
        read_protocol.setOnClickListener { toActivity(UserAgreementActivity::class.java) }
        agree_protocol.setOnCheckedChangeListener { _, isChecked -> canDoNext(isChecked) }
        next_step.setOnClickListener { v ->
            if (v.isSelected) {
                deviceCode = pay_account.text.toString()
                mPresenter?.getDevice(deviceCode, companyCode)
            }
        }
        input_account_help.setOnClickListener { dialog.show() }
        pay_account.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                canDoNext(!s.isNullOrEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        pay_cost_company.setOnClickListener {
            //            toActivity(AddressSelectorActivity::class.java)
        }
    }

    override fun initData() {
        pay_cost_company.text = ConfigUtil.getCompanyName()
    }

    override fun onLoadDeviceSuccess(result: DeviceVM, deviceCode: String) {
        accountDialog.setData(result, deviceCode)
        accountDialog.show()
    }

    override fun onBindDeviceSuccess(deviceCode: String, companyCode: String) {
        toActivity(NewAccountSuccessActivity::class.java, Bundle().apply {
            putString(Constants.IntentKey.DEVICE_CODE, deviceCode)
            putString(Constants.IntentKey.COMPANY_KEY, companyCode)
        })
    }

    override fun onBindDeviceError(e: ApiThrowable) {
        DefaultPromptDialog.builder()
                .setButtonOrientation(DefaultPromptDialog.TypeEnum.BUTTON_HORIZONTAL)
                .setContext(this)
                .setTitleText("提示")
                .setContentText(e.message)
                .setAffirmText("确认")
                .build()
                .show()
    }

    private fun canDoNext(canDo: Boolean) {
        if (canDo) {
            next_step.isSelected = agree_protocol.isChecked && !pay_account.text.isNullOrEmpty()
        } else {
            next_step.isSelected = false
        }
    }
}
