package cn.ygyg.yjf.modular.internet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import cn.ygyg.yjf.R
import cn.ygyg.yjf.utils.BaseViewHolder
import cn.ygyg.yjf.utils.LogUtil

class AddressSelectorAdapter : RecyclerView.Adapter<BaseViewHolder>() {
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

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return if (position % 5 == 0) TYPE_TITLE else TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var layoutId = R.layout.item_address_selector_content
        if (viewType == TYPE_HEADER) {
            layoutId = R.layout.layout_address_location_header
        } else if (viewType == TYPE_TITLE) {
            layoutId = R.layout.item_address_selector_title
        }
        val itemView = LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TYPE_HEADER -> {
            }
            TYPE_TITLE -> {
                val title = holder.findViewById<TextView>(R.id.item_title)
                title.text = list[position - 1]
            }
            else -> {
                val address = holder.findViewById<TextView>(R.id.address_name)
                address.text = list[position - 1]
                address.setOnClickListener { onItemClickListener?.onItemClicked(holder, position - 1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    companion object {
        private val TYPE_HEADER = 0
        private val TYPE_TITLE = 1
        private val TYPE_CONTENT = 2
    }

    interface OnItemClickListener {
        fun onItemClicked(holder: BaseViewHolder, position: Int)
    }
}
