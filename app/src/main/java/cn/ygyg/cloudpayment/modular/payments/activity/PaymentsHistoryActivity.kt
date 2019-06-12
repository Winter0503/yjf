package cn.ygyg.cloudpayment.modular.payments.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.payments.adapter.PaymentsHistoryAdapter
import cn.ygyg.cloudpayment.modular.payments.contract.PaymentsHistoryActivityContract
import cn.ygyg.cloudpayment.modular.payments.presenter.PaymentsHistoryActivityPresenter
import cn.ygyg.cloudpayment.modular.payments.vm.HistoryVM
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import cn.ygyg.cloudpayment.widget.LoadMoreView
import cn.ygyg.cloudpayment.widget.ProgressHeaderView
import com.cn.lib.basic.BaseMvpActivity
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_payments_historty.*

class PaymentsHistoryActivity :
        BaseMvpActivity<PaymentsHistoryActivityContract.Presenter, PaymentsHistoryActivityContract.View>(),
        PaymentsHistoryActivityContract.View {
    private val pageSize = 10
    private val pageNum = 1
    private var isLastPage = false
    private val adapter: PaymentsHistoryAdapter by lazy { PaymentsHistoryAdapter() }

    override fun createPresenter(): PaymentsHistoryActivityContract.Presenter = PaymentsHistoryActivityPresenter(this)

    override fun getContentViewResId(): Int = R.layout.activity_payments_historty
    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_payments_history)
            setLeftImageRes(R.mipmap.back)
        }
        refresh_layout.setHeaderView(ProgressHeaderView(getViewContext()).setTextVisibility(false))
        refresh_layout.setBottomView(LoadMoreView(getViewContext()))
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    override fun initListener() {
        refresh_layout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                mPresenter?.loadPage(1, pageSize)
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                if (!isLastPage) {
                    mPresenter?.loadPage(pageNum + 1, pageSize)
                } else {
                    refresh_layout.finishLoadmore()
                    showToast("没有更多了")
                }
            }
        })
    }

    override fun initData() {
        mPresenter?.loadPage(1, pageSize)
    }

    override fun onLoadCompleted() {
        refresh_layout.finishLoadmore()
        refresh_layout.finishRefreshing()
        if (adapter.itemCount != 0) {
            empty_view.visibility = View.GONE
        } else {
            empty_view.visibility = View.VISIBLE
        }
    }

    override fun onLoadSuccess(list: ArrayList<out HistoryVM>?, firstPage: Boolean, lastPage: Boolean) {
        if (firstPage) {
            adapter.setData(list)
        } else {
            adapter.addData(list)
        }
        isLastPage = lastPage
    }
}
