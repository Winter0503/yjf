package cn.ygyg.yjf.modular.payments.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.ygyg.yjf.R
import cn.ygyg.yjf.utils.BaseViewHolder

class PaymentsHistoryAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_payments_history, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    }
}