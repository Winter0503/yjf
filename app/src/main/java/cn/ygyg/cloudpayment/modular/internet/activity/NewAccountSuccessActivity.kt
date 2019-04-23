package cn.ygyg.cloudpayment.modular.internet.activity

import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.home.activity.MainTabActivity
import cn.ygyg.cloudpayment.modular.payments.activity.PaymentsActivity
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import com.cn.lib.basic.BaseActivity
import kotlinx.android.synthetic.main.activity_new_account_success.*

class NewAccountSuccessActivity : BaseActivity() {
    override fun getContentViewResId(): Int {
        return R.layout.activity_new_account_success
    }

    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_new_account_success)
            setLeftImageRes(R.mipmap.back)
        }
    }

    override fun initListener() {
        to_payments.setOnClickListener {
            toActivity(PaymentsActivity::class.java)
        }
        back_to_main.setOnClickListener {
            toActivity(MainTabActivity::class.java)
        }
    }
}
