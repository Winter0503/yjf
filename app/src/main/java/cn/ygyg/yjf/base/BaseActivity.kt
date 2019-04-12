package cn.ygyg.yjf.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import cn.ygyg.yjf.utils.HeaderBuilder

abstract class BaseActivity : FragmentActivity() {

    private lateinit var header: HeaderBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
        initViews()
        initData()
        initListener()
    }

    abstract fun initViews()

    abstract fun initData()

    abstract fun initListener()

}