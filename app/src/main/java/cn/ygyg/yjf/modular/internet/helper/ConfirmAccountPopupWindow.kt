package cn.ygyg.yjf.modular.internet.helper

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import cn.ygyg.yjf.R
import com.cn.lib.util.DensityUtil

class ConfirmAccountPopupWindow(context: Context) : PopupWindow(context) {
    private lateinit var userName: TextView
    private lateinit var payCostCompany: TextView
    private lateinit var accountName: TextView
    private lateinit var address: TextView
    private lateinit var balance: TextView
    private lateinit var confirm: TextView

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_confirm_account, null)
        height = DensityUtil.dip2px(context, 412f)
        width = DensityUtil.getWidthInPx(context)
        contentView.findViewById<View>(R.id.close).setOnClickListener { dismiss() }
        userName = contentView.findViewById(R.id.user_name)
        payCostCompany = contentView.findViewById(R.id.pay_cost_company)
        accountName = contentView.findViewById(R.id.account_name)
        address = contentView.findViewById(R.id.address)
        balance = contentView.findViewById(R.id.balance)
        confirm = contentView.findViewById(R.id.confirm)
    }


    fun setOnConformClick(l: View.OnClickListener) {
        confirm.setOnClickListener(l)
    }

    fun show(v: View) {
        showAtLocation(v, Gravity.BOTTOM, 0, 0)
    }
}