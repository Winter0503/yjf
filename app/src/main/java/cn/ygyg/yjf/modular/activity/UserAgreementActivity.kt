package cn.ygyg.yjf.modular.activity

import android.view.View
import cn.ygyg.yjf.R
import cn.ygyg.yjf.utils.ResourceUtil
import com.cn.lib.basic.BaseActivity
import kotlinx.android.synthetic.main.include_activity_header.*

/**
 * 用户协议
 * Created by Admin on 2019/4/13.
 */
class UserAgreementActivity : BaseActivity() {

    override fun getContentViewResId(): Int = R.layout.activity_user_agreement

    override fun initViews() {
        super.initViews()
        iv_left.visibility = View.VISIBLE
        iv_left.setImageResource(R.mipmap.back)
        tv_title.visibility = View.VISIBLE
        tv_title.text = ResourceUtil.getString(getViewContext(), R.string.user_agreement)
    }

    override fun initListener() {
        super.initListener()
        iv_left.setOnClickListener {
            this.finish()
        }
    }
}