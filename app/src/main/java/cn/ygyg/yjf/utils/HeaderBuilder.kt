package cn.ygyg.yjf.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.ygyg.yjf.R
import cn.ygyg.yjf.base.BaseViewBuilder

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

        ivLeft = findViewById(R.id.iv_left)
        ivRight = findViewById(R.id.iv_right)

        tvLeft = findViewById(R.id.tv_left)
        tvRight = findViewById(R.id.tv_right)
    }
}