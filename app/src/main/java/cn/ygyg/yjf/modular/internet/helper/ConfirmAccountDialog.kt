package cn.ygyg.yjf.modular.internet.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.ygyg.yjf.R
import com.cn.lib.util.DensityUtil

class ConfirmAccountDialog(context: Context) : Dialog(context) {
    private var userName: TextView
    private var payCostCompany: TextView
    private var accountName: TextView
    private var address: TextView
    private var balance: TextView
    private var confirm: TextView

    init {
        setContentView(R.layout.dialog_confirm_account)
        window?.let {
            it.decorView?.setPadding(0, 0, 0, 0)
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setGravity(Gravity.BOTTOM)
            it.decorView?.setBackgroundColor(Color.TRANSPARENT)
        }

        userName = findViewById(R.id.user_name)
        payCostCompany = findViewById(R.id.pay_cost_company)
        accountName = findViewById(R.id.account_name)
        address = findViewById(R.id.address)
        balance = findViewById(R.id.balance)
        confirm = findViewById(R.id.confirm)
        val close: ImageView = findViewById(R.id.close)
        close.setOnClickListener { dismiss() }
    }


    fun setOnConformClick(l: View.OnClickListener) {
        confirm.setOnClickListener(l)
    }
}