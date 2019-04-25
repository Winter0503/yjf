package cn.ygyg.cloudpayment.modular.home.adapter

import android.content.Context
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import com.cn.lib.basic.BaseRecyclerAdapter
import com.cn.lib.basic.BaseRecyclerViewHolder

/**
 * Created by Admin on 2019/4/16.
 */
class AccountInfoListAdapter(context: Context) : BaseRecyclerAdapter<DeviceVM>(context, R.layout.item_account_info) {
    var onRechargeClickListener: OnRechargeClickListener? = null

    override fun convertView(holder: BaseRecyclerViewHolder, t: DeviceVM, realPosition: Int) {
        holder.setTextView(R.id.account_name, t.userName())
        holder.setTextView(R.id.device_code, t.deviceCode())
        holder.setTextView(R.id.device_address, t.deviceAddress())
        holder.setOnClickListener(R.id.recharge, View.OnClickListener {
            onRechargeClickListener?.onRechargeClicked(realPosition, t)
        })

    }

    interface OnRechargeClickListener {
        fun onRechargeClicked(position: Int, item: DeviceVM)
    }
}