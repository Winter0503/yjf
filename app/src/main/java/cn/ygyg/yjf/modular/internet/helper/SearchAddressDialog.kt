package cn.ygyg.yjf.modular.internet.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.internet.adapter.AddressSearchAdapter
import cn.ygyg.yjf.utils.BaseViewHolder
import cn.ygyg.yjf.widget.CleanUpEditText

class SearchAddressDialog(context: Context) : Dialog(context) {
    private var searchBtn: TextView
    private var search: CleanUpEditText
    private var searchList: RecyclerView

    var onAddressClickListener: OnAddressClickListener? = null

    private val adapter = AddressSearchAdapter()

    init {
        setContentView(R.layout.dialog_search_address)
        window?.let {
            it.decorView?.setPadding(0, 0, 0, 0)
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setGravity(Gravity.BOTTOM)
            it.decorView?.setBackgroundColor(Color.TRANSPARENT)
        }
        searchBtn = findViewById(R.id.search_btn)
        search = findViewById(R.id.search)
        searchList = findViewById(R.id.search_list)

        searchList.layoutManager = LinearLayoutManager(context)
        searchList.adapter = adapter

        adapter.onItemClickListener = object : AddressSearchAdapter.OnItemClickListener {
            override fun onItemClicked(holder: BaseViewHolder, position: Int) {
                dismiss()
                onAddressClickListener?.onAddressClicked()
            }
        }


        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchBtn.setText(if (s.isNullOrEmpty()) R.string.cancel else R.string.search)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        search.setOnEditorActionListener { v, actionId, event ->
            if (event.action == KeyEvent.ACTION_UP) {
                searchAddress(v.text.toString())
            }


            actionId == EditorInfo.IME_ACTION_SEARCH
        }
        searchBtn.setOnClickListener {
            if (search.text.isEmpty()) {
                dismiss()
            } else {
                //do something
                searchAddress(search.text.toString())
            }
        }
    }

    override fun show() {
        super.show()
        search.requestFocus()
    }

    /**
     * 搜索地址
     */
    private fun searchAddress(keyWorld: String) {
        adapter.setData(ArrayList<String>().apply {
            for (i in 1..20) {
                add("i=$i")
            }
        })
    }

    interface OnAddressClickListener {
        fun onAddressClicked()
    }
}
