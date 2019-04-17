package cn.ygyg.yjf.modular.payments.activity

import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.payments.contract.PaymentsActivityContract
import cn.ygyg.yjf.modular.payments.presenter.PaymentsActivityPresenter
import cn.ygyg.yjf.utils.HeaderBuilder
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
    }

    override fun initListener() {
        payments_history.setOnClickListener { toActivity(PaymentsHistoryActivity::class.java) }
        payments.setOnClickListener { toActivity(PaymentsCompleteActivity::class.java) }
    }
}
