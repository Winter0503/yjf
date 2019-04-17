package cn.ygyg.cloudpayment.modular.internet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.utils.BaseViewHolder
import cn.ygyg.cloudpayment.utils.LogUtil

class AddressSearchAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private val list: ArrayList<String> by lazy { ArrayList<String>() }

    var onItemClickListener: OnItemClickListener? = null

    fun setData(list: List<String>?) {
        this.list.clear()
        list?.let {
            this.list.addAll(it)
        }
        LogUtil.i("setData", this.list.size.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutId = R.layout.item_address_selector_content
        val itemView = LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val address = holder.findViewById<TextView>(R.id.address_name)
        address.text = list[position]
        address.setOnClickListener { onItemClickListener?.onItemClicked(holder, position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClicked(holder: BaseViewHolder, position: Int)
    }
}
