package cn.ygyg.yjf.modular.internet.activity

import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.payments.activity.PaymentsActivity
import cn.ygyg.yjf.utils.HeaderBuilder
import com.cn.lib.basic.BaseActivity
import kotlinx.android.synthetic.main.activity_new_account_success.*

class NewAccountSuccessActivity : BaseActivity() {
    override fun getContentViewResId(): Int {
        return R.layout.activity_new_account_success
    }

    override fun initViews() {
        HeaderBuilder(this).setTitle(R.string.activity_title_new_account_success)
    }

    override fun initListener() {
        to_payments.setOnClickListener {
            toActivity(PaymentsActivity::class.java)
        }
    }
}
