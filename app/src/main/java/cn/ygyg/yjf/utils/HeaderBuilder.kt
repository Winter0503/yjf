package cn.ygyg.yjf.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.ygyg.yjf.R

class HeaderBuilder(context: Context) : BaseViewBuilder(context) {
    private lateinit var title: TextView
    private lateinit var tvLeft: TextView
    private lateinit var tvRight: TextView
    private lateinit var ivLeft: ImageView
    private lateinit var ivRight: ImageView

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        return parent.findViewById(R.id.include_header_layout)
    }

    override fun initView() {
        title = findViewById(R.id.tv_title)

        tvLeft = findViewById(R.id.tv_left)
        ivLeft = findViewById(R.id.iv_left)

        tvRight = findViewById(R.id.tv_right)
        ivRight = findViewById(R.id.iv_right)

        var leftOnClick = View.OnClickListener {
            if (getContext() is Activity) {
                var activity = getContext() as Activity
                activity.finish()
            }
        }
        tvLeft.setOnClickListener(leftOnClick)
        ivLeft.setOnClickListener(leftOnClick)
    }

    fun setLeftImageRes(@DrawableRes resId: Int) {
        ivLeft.visibility = if (resId == 0) View.GONE else View.VISIBLE
        ivLeft.setImageResource(resId)
    }

    fun setTitle(text: String) {
        title.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
        title.text = text
    }

    fun setTitle(@StringRes resId: Int) {
        title.visibility = if (resId == 0) View.GONE else View.VISIBLE
        title.setText(resId)
    }
}