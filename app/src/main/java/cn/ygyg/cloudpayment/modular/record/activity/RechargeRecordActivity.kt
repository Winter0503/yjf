package cn.ygyg.cloudpayment.modular.record.activity

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.record.adapter.RechargeRecordAdapter
import cn.ygyg.cloudpayment.modular.record.contract.RechargeRecordContract
import cn.ygyg.cloudpayment.modular.record.presenter.RechargeRecordPresenter
import cn.ygyg.cloudpayment.widget.LoadMoreView
import cn.ygyg.cloudpayment.widget.ProgressHeaderView
import com.cn.lib.basic.BaseMvpActivity
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_recharge_record.*
import kotlinx.android.synthetic.main.include_activity_header.*

/**
 * 充值记录
 * Created by Admin on 2019/4/17.
 */
class RechargeRecordActivity : BaseMvpActivity<RechargeRecordContract.Presenter, RechargeRecordContract.View>(), RechargeRecordContract.View {

    private val mAdapter: RechargeRecordAdapter by lazy {
        RechargeRecordAdapter(getViewContext())
    }

    override fun getContentViewResId(): Int = R.layout.activity_recharge_record

    override fun createPresenter(): RechargeRecordContract.Presenter = RechargeRecordPresenter(this)

    override fun initViews() {
        super.initViews()
        iv_left.setImageResource(R.mipmap.back)
        iv_left.visibility = View.VISIBLE
        tv_title.visibility = View.VISIBLE
        tv_title.text = "充值记录"
        recycler_record.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(getViewContext())
        }
        layout_refresh.let {
            it.setHeaderView(ProgressHeaderView(getViewContext()).setTextVisibility(false))
            it.setBottomView(LoadMoreView(getViewContext()))
        }
    }

    override fun initListener() {
        super.initListener()
        iv_left.setOnClickListener { this.finish() }
        layout_refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                Handler().postDelayed({
                    refreshLayout.finishRefreshing()
                }, 2000)
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout) {
                Handler().postDelayed({
                    refreshLayout.finishLoadmore()
                }, 2000)
            }
        })
    }
}