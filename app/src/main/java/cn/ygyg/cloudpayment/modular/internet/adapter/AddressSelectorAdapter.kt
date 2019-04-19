package cn.ygyg.cloudpayment.modular.internet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.internet.vm.CityVM
import cn.ygyg.cloudpayment.utils.BaseViewHolder

class AddressSelectorAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private val list: ArrayList<CityVM> by lazy { ArrayList<CityVM>() }

    var onItemClickListener: OnItemClickListener? = null
    fun setData(list: List<CityVM>?) {
        this.list.clear()
        list?.let {
            this.list.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun setItem(position: Int, item: CityVM) {
        if (position < list.size) {
            list[position] = item
            notifyItemChanged(position, item)
        }
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(item: CityVM) {
        list.add(item)
        notifyItemInserted(list.size - 1)
    }

    fun addData(list: List<CityVM>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return if (getItem(position).isRealCity()) TYPE_CONTENT else TYPE_TITLE
    }

    private fun getItem(position: Int): CityVM {
        return list[position]
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
        val cityVM = list[position]
        when (viewType) {
            TYPE_HEADER -> {
                val location = holder.findViewById<TextView>(R.id.location_city)
                location.text = cityVM.cityShowName()
                location.setOnClickListener { onItemClickListener?.onLocationClicked(cityVM) }
            }
            TYPE_TITLE -> {
                val title = holder.findViewById<TextView>(R.id.item_title)
                title.text = cityVM.cityShowName()
            }
            else -> {
                val address = holder.findViewById<TextView>(R.id.address_name)
                address.text = cityVM.cityShowName()
                address.setOnClickListener { onItemClickListener?.onItemClicked(holder, position - 1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        private val TYPE_HEADER = 0
        private val TYPE_TITLE = 1
        private val TYPE_CONTENT = 2
    }

    interface OnItemClickListener {
        fun onItemClicked(holder: BaseViewHolder, position: Int)
        //定位按钮被点击
        fun onLocationClicked(cityVM: CityVM)
    }
}
