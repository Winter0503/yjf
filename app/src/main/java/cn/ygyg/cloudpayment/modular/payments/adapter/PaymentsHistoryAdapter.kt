package cn.ygyg.cloudpayment.modular.payments.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.payments.vm.HistoryVM
import cn.ygyg.cloudpayment.utils.BaseViewHolder

class PaymentsHistoryAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val list = ArrayList<HistoryVM>()


    fun setData(list: ArrayList<HistoryVM>?) {
        this.list.clear()
        list?.let {
            this.list.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun addData(list: ArrayList<HistoryVM>?) {
        list?.let {
            this.list.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_payments_history, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = list[position]
        holder.findViewById<TextView>(R.id.user_name).text = item.userName()
        holder.findViewById<TextView>(R.id.pay_amount).text = item.payAmount()
        holder.findViewById<TextView>(R.id.pay_state).text = item.payState()
        holder.findViewById<TextView>(R.id.account_code).text = item.accountCode()
        holder.findViewById<TextView>(R.id.pay_mode).text = item.payMode()
        holder.findViewById<TextView>(R.id.pay_time).text = item.payTime()
    }
}