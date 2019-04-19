package cn.ygyg.cloudpayment.modular.internet.activity

import android.support.v7.widget.LinearLayoutManager
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.modular.internet.adapter.AddressSelectorAdapter
import cn.ygyg.cloudpayment.modular.internet.contract.AddressSelectorActivityContract
import cn.ygyg.cloudpayment.modular.internet.entity.AddressCityEntity
import cn.ygyg.cloudpayment.modular.internet.entity.CityVM
import cn.ygyg.cloudpayment.modular.internet.helper.CompanySelectDialog
import cn.ygyg.cloudpayment.modular.internet.helper.SearchAddressDialog
import cn.ygyg.cloudpayment.modular.internet.presenter.AddressSelectorActivityPresenter
import cn.ygyg.cloudpayment.utils.BaseViewHolder
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import cn.ygyg.cloudpayment.utils.LocationUtil
import com.amap.api.location.AMapLocation
import com.cn.lib.basic.BaseMvpActivity
import com.cn.lib.retrofit.network.util.LogUtil
import com.cn.lib.util.ToastUtil
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

    private var haveLocation = false

    override fun getContentViewResId(): Int = R.layout.activity_address_selector

    override fun createPresenter(): AddressSelectorActivityContract.Presenter = AddressSelectorActivityPresenter(this)

    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_address_select)
            setLeftImageRes(R.mipmap.back)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        adapter.addItem(AddressCityEntity().apply {
            address = "定位中"
        })
        recycler.adapter = adapter

        LocationUtil.startLocation(object : LocationUtil.Callback {
            override fun onStart() {

            }

            override fun onSuccess(location: AMapLocation) {
                haveLocation = true
                LogUtil.i("location", location.city)
                adapter.setItem(0, AddressCityEntity().apply {
                    address = location.city
                })
            }

            override fun onFailed(errCode: Int) {
                ToastUtil.showErrorToast(this@AddressSelectorActivity, "定位失败")
                adapter.removeItem(0)
            }
        })
    }

    override fun initListener() {
        search.setOnClickListener { searchAddressDialog.show() }
        adapter.onItemClickListener = object : AddressSelectorAdapter.OnItemClickListener {
            override fun onItemClicked(holder: BaseViewHolder, position: Int) {
                companySelectDialog.show()
            }

            override fun onLocationClicked(cityVM: CityVM) {
                if (haveLocation) {
                    ToastUtil.showToast(this@AddressSelectorActivity, cityVM.getCityName())
                }
            }
        }
    }
}
