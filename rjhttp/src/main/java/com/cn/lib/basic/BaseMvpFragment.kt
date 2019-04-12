package com.cn.lib.basic

import android.os.Bundle


/**
 * Created by admin on 2017/4/17.
 */


abstract class BaseMvpFragment<P : IBasePresenter<IBaseView>> : BaseFragment(), IBaseView {
    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mPresenter == null) {
            this.mPresenter = createPresenter()
        } else {
            this.mPresenter!!.attachView(this)
        }
    }

    protected abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.detachView()
            mPresenter = null
        }
    }

}
