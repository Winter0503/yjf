package cn.ygyg.yjf.modular.internet.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

import cn.ygyg.yjf.R

class InquireAccountDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.dialog_inquire_account)
        window?.decorView?.setPadding(0, 0, 0, 0);
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        findViewById<View>(R.id.close).setOnClickListener { dismiss() }
    }
}
