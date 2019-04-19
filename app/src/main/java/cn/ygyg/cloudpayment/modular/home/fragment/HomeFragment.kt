package cn.ygyg.cloudpayment.modular.home.fragment

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.R.id.recycler_view
import cn.ygyg.cloudpayment.modular.home.adapter.AccountInfoListAdapter
import cn.ygyg.cloudpayment.modular.home.contract.HomeContract
import cn.ygyg.cloudpayment.modular.home.presenter.HomePresenter
import cn.ygyg.cloudpayment.modular.internet.activity.AddressSelectorActivity
import cn.ygyg.cloudpayment.modular.internet.activity.NewAccountActivity
import cn.ygyg.cloudpayment.widget.LoadMoreView
import cn.ygyg.cloudpayment.widget.ProgressHeaderView
import com.cn.lib.basic.BaseFragment
import com.cn.lib.basic.BaseMvpFragment
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvpFragment<HomeContract.Presenter, HomeContract.View>(), HomeContract.View {
    override fun loaderSuccess(mutableList: MutableList<String>?) {
        mutableList?.let {
            //不为空时执行
            mAdapter.setNewList(mutableList)

        } ?: let {
            //为空时执行
            val firstView = layoutInflater.inflate(R.layout.layout_first_into, recycler_view, false)
            mAdapter.addHeaderView(firstView)
            firstView.findViewById<View>(R.id.btn_recharge).setOnClickListener {
                toActivity(AddressSelectorActivity::class.java)
            }
            firstView.findViewById<View>(R.id.layout_add_account).setOnClickListener {
                toActivity(AddressSelectorActivity::class.java)
            }
        }
    }

    override fun createPresenter(): HomeContract.Presenter = HomePresenter(this)

    private var refreshLayout: TwinklingRefreshLayout? = null

    private val mAdapter: AccountInfoListAdapter by lazy {
        AccountInfoListAdapter(getViewContext())
    }

    override val contentViewResId: Int = R.layout.fragment_home

    override fun initViews(v: View) {
        recycler_view.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(getViewContext())
        }
        mAdapter.addHeaderView(layoutInflater.inflate(R.layout.layout_banner, recycler_view, false))
        refreshLayout = findViewById(R.id.layout_refresh)
        refreshLayout?.setHeaderView(ProgressHeaderView(getViewContext()).setTextVisibility(false))
        refreshLayout?.setBottomView(LoadMoreView(getViewContext()))
    }

    override fun initListener(v: View) {
        refreshLayout?.setOnRefreshListener(object : RefreshListenerAdapter() {
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

    override fun loaderData() {
        mPresenter?.loaderData()
    }


}
