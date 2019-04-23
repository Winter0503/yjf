package cn.ygyg.cloudpayment.modular.internet.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.internet.contract.NewAccountActivityContract
import cn.ygyg.cloudpayment.modular.internet.helper.ConfirmAccountDialog
import cn.ygyg.cloudpayment.modular.internet.helper.InquireAccountDialog
import cn.ygyg.cloudpayment.modular.internet.presenter.NewAccountActivityPresenter
import cn.ygyg.cloudpayment.modular.register.activity.UserAgreementActivity
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_new_account.*

class NewAccountActivity : BaseMvpActivity<NewAccountActivityContract.Presenter, NewAccountActivityContract.View>(),
        NewAccountActivityContract.View {
    override fun createPresenter(): NewAccountActivityContract.Presenter {
        return NewAccountActivityPresenter(this)
    }

    private val headerBuilder: HeaderBuilder by lazy { HeaderBuilder(this) }
    private val accountDialog: ConfirmAccountDialog by lazy {
        ConfirmAccountDialog(this).apply {
            setOnConformClick(View.OnClickListener { toActivity(NewAccountSuccessActivity::class.java) })
        }
    }
    private val dialog: InquireAccountDialog by lazy { InquireAccountDialog(this) }

    override fun getContentViewResId(): Int {
        return R.layout.activity_new_account
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            //TODO AddressSelectorActivity.companySelectDialog 城市 缴费单位获取
        }
    }

    override fun initViews() {
        bundle?.let {
            //TODO AddressSelectorActivity.companySelectDialog 城市 缴费单位获取
        }
        headerBuilder.setLeftImageRes(R.mipmap.back)
        headerBuilder.setTitle(R.string.activity_title_add_new_account)
        pay_account.requestFocus()
    }

    override fun initListener() {
        read_protocol.setOnClickListener { toActivity(UserAgreementActivity::class.java) }
        agree_protocol.setOnCheckedChangeListener { _, isChecked -> canDoNext(isChecked) }
        next_step.setOnClickListener { v ->
            if (v.isSelected) {
                accountDialog.show()
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
            toActivity(AddressSelectorActivity::class.java)
        }
    }

    private fun canDoNext(canDo: Boolean) {
        if (canDo) {
            next_step.isSelected = agree_protocol.isChecked && !pay_account.text.isNullOrEmpty()
        } else {
            next_step.isSelected = false
        }
    }
}
