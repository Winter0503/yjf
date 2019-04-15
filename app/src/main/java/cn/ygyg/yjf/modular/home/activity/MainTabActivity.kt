package cn.ygyg.yjf.modular.home.activity

import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.home.fragment.HomeFragment
import cn.ygyg.yjf.modular.my.fragment.MyFragment
import cn.ygyg.yjf.utils.ResourceUtil
import com.cn.lib.basic.BaseIndicatorActivity
import com.cn.lib.weight.indicator.TabInfo
import java.util.*

class HomeActivity:BaseIndicatorActivity() {
    override fun initTabsInfo(tabs: ArrayList<TabInfo>) {
        tabs.add(TabInfo(1, ResourceUtil.getString(getViewContext(), R.string.tab_home), R.mipmap.tab_home_normal, R.mipmap.tab_home_press, HomeFragment::class.java))
        tabs.add(TabInfo(2, ResourceUtil.getString(getViewContext(), R.string.tab_my), R.mipmap.tab_my_normal, R.mipmap.tab_my_press, MyFragment::class.java))
    }

    override fun getTagIndicatorViewId(): Int = R.id.layout_tab

    override fun getContentViewResId(): Int = R.layout.activity_home
}