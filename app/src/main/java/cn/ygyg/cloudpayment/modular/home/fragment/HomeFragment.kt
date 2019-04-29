package cn.ygyg.cloudpayment.modular.home.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.util.Log
import android.view.View
import cn.ygyg.cloudpayment.R.layout
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.dialog.DefaultPromptDialog
import cn.ygyg.cloudpayment.modular.home.adapter.AccountInfoListAdapter
import cn.ygyg.cloudpayment.modular.home.contract.HomeContract
import cn.ygyg.cloudpayment.modular.home.presenter.HomePresenter
import cn.ygyg.cloudpayment.modular.internet.activity.AddressSelectorActivity
import cn.ygyg.cloudpayment.modular.internet.activity.NewAccountActivity
import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import cn.ygyg.cloudpayment.modular.payments.activity.PaymentsActivity
import com.cn.lib.basic.BaseMvpFragment
import com.cn.lib.util.ToastUtil
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvpFragment<HomeContract.Presenter, HomeContract.View>(), HomeContract.View {

    override fun createPresenter(): HomeContract.Presenter = HomePresenter(this)

    private val mAdapter: AccountInfoListAdapter by lazy {
        AccountInfoListAdapter(getViewContext())
    }

    private val firstView: View by lazy {
        val view = layoutInflater.inflate(layout.layout_first_into, recycler_view, false)
        view.findViewById<View>(cn.ygyg.cloudpayment.R.id.btn_recharge).setOnClickListener {
            toActivity(NewAccountActivity::class.java)
        }
        view.findViewById<View>(cn.ygyg.cloudpayment.R.id.layout_add_account).setOnClickListener {
            toActivity(NewAccountActivity::class.java)
        }
        view
    }

    override val contentViewResId: Int = layout.fragment_home


    override fun initViews(v: View) {
        recycler_view.let {
            it.clearAnimation()
            (it.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(getViewContext())
        }
        mAdapter.addHeaderView(layoutInflater.inflate(layout.layout_banner, recycler_view, false))
    }

    override fun initListener(v: View) {
        refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
//                Handler().postDelayed({
//                    refreshLayout.finishRefreshing()
//                }, 2000)
                mPresenter?.loaderData()
            }
//            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout) {
//                Handler().postDelayed({
//                    refreshLayout.finishLoadmore()
//                    //设置不可上拉加载更多
//                    refreshLayout.setEnableLoadmore(false)
//                    //取消上拉回弹效果
//                    refreshLayout.setEnableOverScroll(false)
//                }, 2000)
//            }
        })

        mAdapter.onCustomClickListener = object : AccountInfoListAdapter.OnCustomClickListener {
            override fun onRechargeClicked(position: Int, item: DeviceVM) {
                toActivity(PaymentsActivity::class.java, Bundle().apply {
                    putString(Constants.IntentKey.DEVICE_CODE, item.deviceCode())
                    putString(Constants.IntentKey.COMPANY_CODE, item.companyCode())
                })
            }

            override fun onItemLongClicked(position: Int, item: DeviceVM) {
                DefaultPromptDialog.builder()
                        .setContext(getViewContext())
                        .setButtonOrientation(DefaultPromptDialog.TypeEnum.BUTTON_HORIZONTAL)
                        .setContentText("确定删除该账户信息吗？")
                        .setAffirmText("确定")
                        .setCancelText("取消")
                        .onPromptDialogButtonListener(object : DefaultPromptDialog.DefaultPromptDialogButtonListener() {
                            override fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean {
                                mPresenter?.unBindDevice(position, item)
                                return super.clickPositiveButton(dialog)
                            }
                        })
                        .build()
                        .show()
            }
        }
    }

    override fun loaderData() {
        refreshLayout.startRefresh()
    }


    override fun loaderSuccess(response: ArrayList<out DeviceVM>?) {
        if (mAdapter.getFooterLayoutCount() == 0) {
            //为空时执行
            mAdapter.addFooterView(firstView)
        }
        //当列表为空时显示提示布局，否则隐藏提示布局
        if (response != null && response.size != 0) {
            firstView.findViewById<View>(cn.ygyg.cloudpayment.R.id.pay_item).visibility = View.GONE
            firstView.findViewById<View>(cn.ygyg.cloudpayment.R.id.developing).visibility = View.GONE
        } else {
            firstView.findViewById<View>(cn.ygyg.cloudpayment.R.id.pay_item).visibility = View.VISIBLE
            firstView.findViewById<View>(cn.ygyg.cloudpayment.R.id.developing).visibility = View.VISIBLE
        }
        response?.let {
            //不为空时执行
            mAdapter.setNewList(it.toMutableList())
        }
        refreshLayout.finishRefreshing()
        setHasLoadedOnce(true)
    }

    override fun unbindSuccess(position: Int, device: DeviceVM) {
        context?.let { ToastUtil.showToast(it, "删除成功") }
        mAdapter.removeItem(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = [Tag("refreshDevice")])
    fun refreshList(isRefresh: String) {
        Log.e("TAG", "=======>>>>>isRefresh : $isRefresh")
        refreshLayout.startRefresh()
    }

}
