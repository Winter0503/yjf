package cn.ygyg.yjf.modular.payments.activity

import android.support.v7.widget.LinearLayoutManager
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.payments.adapter.PaymentsHistoryAdapter
import cn.ygyg.yjf.modular.payments.contract.PaymentsHistoryActivityContract
import cn.ygyg.yjf.modular.payments.presenter.PaymentsHistoryActivityPresenter
import cn.ygyg.yjf.utils.HeaderBuilder
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_payments_historty.*

class PaymentsHistoryActivity :
        BaseMvpActivity<PaymentsHistoryActivityContract.Presenter, PaymentsHistoryActivityContract.View>(),
        PaymentsHistoryActivityContract.View {

    private val adapter: PaymentsHistoryAdapter by lazy { PaymentsHistoryAdapter() }

    override fun createPresenter(): PaymentsHistoryActivityContract.Presenter = PaymentsHistoryActivityPresenter(this)

    override fun getContentViewResId(): Int = R.layout.activity_payments_historty
    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_payments_history)
            setLeftImageRes(R.mipmap.back)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }
}
