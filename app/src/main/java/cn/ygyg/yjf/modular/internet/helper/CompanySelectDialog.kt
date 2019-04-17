package cn.ygyg.yjf.modular.internet.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.internet.adapter.CompanySelectorAdapter

class CompanySelectDialog(context: Context) : Dialog(context) {
    private val adapter: CompanySelectorAdapter by lazy { CompanySelectorAdapter() }
    private val recycler: RecyclerView

    var onCompanyConfirmListener: OnCompanyConfirmListener? = null

    init {
        setContentView(R.layout.dialog_company_select)
        window?.let {
            it.decorView?.setPadding(0, 0, 0, 0)
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setGravity(Gravity.BOTTOM)
            it.decorView?.setBackgroundColor(Color.TRANSPARENT)
        }
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        findViewById<View>(R.id.close).setOnClickListener { dismiss() }
        findViewById<View>(R.id.confirm).setOnClickListener {
            dismiss()
            onCompanyConfirmListener?.onCompanyConfirm()
        }
    }

    interface OnCompanyConfirmListener {
        fun onCompanyConfirm()
    }
}
