package cn.ygyg.yjf.modular.home.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.home.adapter.AccountInfoListAdapter
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
        mAdapter.addHeaderView(layoutInflater.inflate(R.layout.layout_first_into,recycler_view ,false))
    }

    override fun initListener(v: View) {

    }

    override fun loaderData() {

    }


}
