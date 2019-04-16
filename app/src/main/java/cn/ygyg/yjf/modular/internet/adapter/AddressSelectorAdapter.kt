package cn.ygyg.yjf.modular.internet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.ygyg.yjf.R
import cn.ygyg.yjf.utils.BaseViewHolder

class AddressSelectorAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private val list: List<String>? = null

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return if (position % 5 == 0) TYPE_HEADER else TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var layoutId = R.layout.item_address_selector_content
        if (viewType == TYPE_HEADER) {
            layoutId = R.layout.layout_address_loaction_header
        } else if (viewType == TYPE_TITLE) {
            layoutId = R.layout.item_address_selector_title
        }
        val itemView = LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == TYPE_HEADER) {
//            layoutId = R.layout.layout_address_loaction_header
        } else if (viewType == TYPE_TITLE) {
//            layoutId = R.layout.item_address_selector_title
        }
    }

    override fun getItemCount(): Int {
        val size = list?.size ?: 0
        return size + 1
    }

    companion object {
        private val TYPE_HEADER = 0
        private val TYPE_TITLE = 1
        private val TYPE_CONTENT = 2
    }
}
