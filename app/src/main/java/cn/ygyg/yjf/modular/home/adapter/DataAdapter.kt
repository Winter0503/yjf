package cn.ygyg.yjf.modular.home.adapter

import android.content.Context
import android.widget.TextView
import cn.ygyg.yjf.R

/**
 * Created by Admin on 2019/4/15.
 */
class DataAdapter(context: Context) : ListBaseAdapter<String>(context) {
    override fun getLayoutId(): Int = R.layout.item_home_recharge_history

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        holder.getView<TextView>(R.id.tv_name).text = dataList[position]
    }
}