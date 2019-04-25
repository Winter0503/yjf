package cn.ygyg.cloudpayment.modular.home.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.dialog.DefaultPromptDialog
import cn.ygyg.cloudpayment.modular.home.adapter.AccountInfoListAdapter
import cn.ygyg.cloudpayment.modular.home.contract.HomeContract
import cn.ygyg.cloudpayment.modular.home.presenter.HomePresenter
import cn.ygyg.cloudpayment.modular.internet.activity.AddressSelectorActivity
import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import cn.ygyg.cloudpayment.modular.payments.activity.PaymentsActivity
import com.cn.lib.basic.BaseMvpFragment
import com.cn.lib.basic.BaseRecyclerAdapter
import com.cn.lib.util.ToastUtil
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvpFragment<HomeContract.Presenter, HomeContract.View>(), HomeContract.View {
    override fun loaderSuccess(response: ArrayList<out DeviceVM>?) {
        response?.let {
            //不为空时执行
            mAdapter.setNewList(it.toMutableList())
        }
        //为空时执行
        val firstView = layoutInflater.inflate(R.layout.layout_first_into, recycler_view, false)
        if (mAdapter.itemCount != 0) {
            firstView.findViewById<View>(R.id.pay_item).visibility = View.GONE
            firstView.findViewById<View>(R.id.developing).visibility = View.GONE
        }
        mAdapter.addFooterView(firstView)
        firstView.findViewById<View>(R.id.btn_recharge).setOnClickListener {
            toActivity(AddressSelectorActivity::class.java)
        }
        firstView.findViewById<View>(R.id.layout_add_account).setOnClickListener {
            toActivity(AddressSelectorActivity::class.java)
        }


        setHasLoadedOnce(true)
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
                    //设置不可上拉加载更多
                    refreshLayout.setEnableLoadmore(false)
                    //取消上拉回弹效果
                    refreshLayout.setEnableOverScroll(false)
                }, 2000)
            }
        })

        mAdapter.onCustomClickListener = object : AccountInfoListAdapter.OnCustomClickListener {
            override fun onRechargeClicked(position: Int, item: DeviceVM) {
                toActivity(PaymentsActivity::class.java, Bundle().apply {
                    putString(Constants.IntentKey.DEVICE_CODE, item.deviceCode())
                    putString(Constants.IntentKey.COMPANY_CODE, item.companyCode())
                })
            }

            override fun onItemLongClicked(position: Int, item: DeviceVM) {
                context?.let {
                    DefaultPromptDialog.builder()
                            .setContext(it)
                            .setCancelText("取消")
                            .setAffirmText("删除")
                            .setButtonOrientation(DefaultPromptDialog.TypeEnum.BUTTON_VERTICAL)
                            .onPromptDialogButtonListener(object : DefaultPromptDialog.PromptDialogButtonListener {
                                override fun clickContentButton(dialog: DefaultPromptDialog): Boolean {
                                    return false
                                }

                                override fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean {
                                    dialog.dismiss()
                                    mPresenter?.unBindDevice(position, item)
                                    return true
                                }

                                override fun clickNegativeButton(dialog: DefaultPromptDialog): Boolean {
                                    dialog.dismiss()
                                    return true
                                }
                            })
                            .build()
                            .show()
                }
            }
        }
    }

    override fun loaderData() {
        mPresenter?.loaderData()
    }

    override fun unbindSuccess(position: Int, device: DeviceVM) {
        context?.let { ToastUtil.showToast(it, "成功") }
        mAdapter.removeItem(position)
    }
}
