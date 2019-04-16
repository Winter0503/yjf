package cn.ygyg.yjf.modular.home.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.home.adapter.AccountInfoListAdapter
import cn.ygyg.yjf.modular.internet.activity.NewAccountActivity
import com.cn.lib.basic.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {
    private val mAdapter: AccountInfoListAdapter by lazy {
        AccountInfoListAdapter(getViewContext())
    }

    override val contentViewResId: Int = R.layout.fragment_home

    override fun initViews(v: View) {
        recycler_view.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(getViewContext())
        }
        val firstView = layoutInflater.inflate(R.layout.layout_first_into, recycler_view, false)
        mAdapter.addHeaderView(layoutInflater.inflate(R.layout.layout_banner, recycler_view, false))
        mAdapter.addHeaderView(firstView)
        firstView.findViewById<View>(R.id.btn_recharge).setOnClickListener {
            toActivity(NewAccountActivity::class.java)
        }
        firstView.findViewById<View>(R.id.layout_add_account).setOnClickListener {
            toActivity(NewAccountActivity::class.java)
        }
    }

    override fun initListener(v: View) {

    }

    override fun loaderData() {

    }


}
