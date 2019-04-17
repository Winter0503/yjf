package cn.ygyg.yjf.modular.internet.activity

import android.support.v7.widget.LinearLayoutManager
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.internet.adapter.AddressSelectorAdapter
import cn.ygyg.yjf.modular.internet.contract.AddressSelectorActivityContract
import cn.ygyg.yjf.modular.internet.helper.CompanySelectDialog
import cn.ygyg.yjf.modular.internet.helper.SearchAddressDialog
import cn.ygyg.yjf.modular.internet.presenter.AddressSelectorActivityPresenter
import cn.ygyg.yjf.utils.BaseViewHolder
import cn.ygyg.yjf.utils.HeaderBuilder
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_address_selector.*

class AddressSelectorActivity :
        BaseMvpActivity<AddressSelectorActivityContract.Presenter, AddressSelectorActivityContract.View>(),
        AddressSelectorActivityContract.View {
    private val adapter: AddressSelectorAdapter by lazy { AddressSelectorAdapter() }
    private val searchAddressDialog: SearchAddressDialog by lazy {
        SearchAddressDialog(this).apply {
            onAddressClickListener = object : SearchAddressDialog.OnAddressClickListener {
                override fun onAddressClicked() {
                    companySelectDialog.show()
                }
            }
        }
    }
    private val companySelectDialog: CompanySelectDialog by lazy {
        CompanySelectDialog(this).apply {
            onCompanyConfirmListener = object : CompanySelectDialog.OnCompanyConfirmListener {
                override fun onCompanyConfirm() {
                    finish()
                }
            }
        }
    }

    override fun getContentViewResId(): Int = R.layout.activity_address_selector

    override fun createPresenter(): AddressSelectorActivityContract.Presenter = AddressSelectorActivityPresenter(this)

    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_address_select)
            setLeftImageRes(R.mipmap.back)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        adapter.setData(getAddressList())
        recycler.adapter = adapter
    }

    override fun initListener() {
        search.setOnClickListener { searchAddressDialog.show() }
        adapter.onItemClickListener = object : AddressSelectorAdapter.OnItemClickListener {
            override fun onItemClicked(holder: BaseViewHolder, position: Int) {
                companySelectDialog.show()
            }
        }
    }

    private fun getAddressList(): List<String>? {
        return ArrayList<String>().apply {
            for (i in 1..20) {
                add("i=$i")
            }
        }
    }
}
