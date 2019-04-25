package cn.ygyg.cloudpayment.modular.internet.activity

import android.os.Bundle
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.modular.home.activity.MainTabActivity
import cn.ygyg.cloudpayment.modular.payments.activity.PaymentsActivity
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import com.cn.lib.basic.BaseActivity
import kotlinx.android.synthetic.main.activity_new_account_success.*

class NewAccountSuccessActivity : BaseActivity() {

    private var deviceCode = ""
    private var companyCode = ""
    override fun getContentViewResId(): Int {
        return R.layout.activity_new_account_success
    }

    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_new_account_success)
            setLeftImageRes(R.mipmap.back)
        }
        bundle?.let {
            deviceCode = it.getString(Constants.IntentKey.DEVICE_CODE, "")
            companyCode = it.getString(Constants.IntentKey.COMPANY_CODE, "")
        }
    }

    override fun initListener() {
        to_payments.setOnClickListener {
            toActivity(PaymentsActivity::class.java, Bundle().apply {
                putString(Constants.IntentKey.DEVICE_CODE, deviceCode)
                putString(Constants.IntentKey.COMPANY_CODE, companyCode)
            })
        }
        back_to_main.setOnClickListener {
            toActivity(MainTabActivity::class.java)
        }
    }
}
