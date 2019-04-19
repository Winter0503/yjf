package cn.ygyg.cloudpayment.modular.internet.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.ArrayMap
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.modular.internet.adapter.AddressSelectorAdapter
import cn.ygyg.cloudpayment.modular.internet.contract.AddressSelectorActivityContract
import cn.ygyg.cloudpayment.modular.internet.entity.AddressCityEntity
import cn.ygyg.cloudpayment.modular.internet.vm.CityVM
import cn.ygyg.cloudpayment.modular.internet.helper.CompanySelectDialog
import cn.ygyg.cloudpayment.modular.internet.helper.SearchAddressDialog
import cn.ygyg.cloudpayment.modular.internet.presenter.AddressSelectorActivityPresenter
import cn.ygyg.cloudpayment.modular.internet.vm.CompanyVM
import cn.ygyg.cloudpayment.utils.BaseViewHolder
import cn.ygyg.cloudpayment.utils.HeaderBuilder
import cn.ygyg.cloudpayment.utils.LocationUtil
import cn.ygyg.cloudpayment.widget.LoadMoreView
import cn.ygyg.cloudpayment.widget.ProgressHeaderView
import cn.ygyg.cloudpayment.widget.SideBarView
import com.amap.api.location.AMapLocation
import com.cn.lib.basic.BaseMvpActivity
import com.cn.lib.util.ToastUtil
import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict
import kotlinx.android.synthetic.main.activity_address_selector.*

class AddressSelectorActivity :
        BaseMvpActivity<AddressSelectorActivityContract.Presenter, AddressSelectorActivityContract.View>(),
        AddressSelectorActivityContract.View {
    private var dataSource: ArrayList<CityVM>? = null

    private var city: CityVM? = null
    private val adapter: AddressSelectorAdapter by lazy { AddressSelectorAdapter() }
    private val searchAddressDialog: SearchAddressDialog by lazy {
        SearchAddressDialog(this).apply {
            onAddressClickListener = object : SearchAddressDialog.OnAddressClickListener {
                override fun onAddressClicked(city: CityVM) {
                    this@AddressSelectorActivity.city = city
                    mPresenter?.getCompanyByCity(city)
                    companySelectDialog.show()

                }
            }
            getDataSource = object : SearchAddressDialog.DataSourceGetter {
                override fun dataSource(): ArrayList<CityVM>? {
                    return dataSource
                }
            }
        }
    }

    private val companySelectDialog: CompanySelectDialog by lazy {
        CompanySelectDialog(this).apply {
            onCompanyConfirmListener = object : CompanySelectDialog.OnCompanyConfirmListener {
                override fun onCompanyConfirm(company: CompanyVM?) {
                    company?.let {
                        if (forResult) {
                            setResult(Activity.RESULT_OK, Intent().apply {
                                //TODO  NewAccountActivity.onActivityResult 城市 缴费单位传递
                            })
                            finish()
                        } else {
                            toActivity(NewAccountActivity::class.java, Bundle().apply {
                                //TODO NewAccountActivity.initViews 城市 缴费单位传递
                            })
                        }
                    }
                }
            }
        }
    }

    private var forResult = false
    private var haveLocation = false
    private var titlePositionMap: ArrayMap<String, Int>? = null

    override fun getContentViewResId(): Int = R.layout.activity_address_selector

    override fun createPresenter(): AddressSelectorActivityContract.Presenter = AddressSelectorActivityPresenter(this)

    override fun initViews() {
        bundle?.let {
            forResult = it.getBoolean(Constants.IntentKey.FOR_RESULT, false)
        }
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)))
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_address_select)
            setLeftImageRes(R.mipmap.back)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        adapter.addItem(AddressCityEntity().apply {
            cityName = "定位中"
        })
        recycler.adapter = adapter

        refreshLayout.setHeaderView(ProgressHeaderView(getViewContext()).setTextVisibility(false))
        refreshLayout.setBottomView(LoadMoreView(getViewContext()))
    }

    override fun initListener() {
        search.setOnClickListener { searchAddressDialog.show() }
        adapter.onItemClickListener = object : AddressSelectorAdapter.OnItemClickListener {
            override fun onItemClicked(holder: BaseViewHolder, position: Int) {
                companySelectDialog.show()
            }

            override fun onLocationClicked(cityVM: CityVM) {
                if (haveLocation) {
                    ToastUtil.showToast(this@AddressSelectorActivity, cityVM.cityShowName())
                }
            }
        }
        sideBar.onSideBarTouchListener = object : SideBarView.OnSideBarTouchListener {
            override fun onTouchChanged(char: String, position: Int) {
                titlePositionMap?.let {
                    it[char]?.let {
                        recycler.smoothScrollToPosition(it.plus(1))
                    }
                }
            }

            override fun onTouching(touching: Boolean) {
            }

        }

    }

    override fun initData() {
        mPresenter?.loadCityList()
        LocationUtil.startLocation(object : LocationUtil.Callback {
            override fun onStart() {

            }

            override fun onSuccess(location: AMapLocation) {
                haveLocation = true
                val pinyin = Pinyin.toPinyin(location.city, "")
                adapter.setItem(0, AddressCityEntity().apply {
                    cityName = location.city
                    cityPinyin = pinyin
                })
            }

            override fun onFailed(errCode: Int) {
                ToastUtil.showErrorToast(this@AddressSelectorActivity, "定位失败")
                adapter.removeItem(0)
            }
        })
    }


    override fun onLoadCityListSuccess(response: ArrayList<CityVM>) {
        this.dataSource = response
        mPresenter?.addTitleItem(response)
    }

    override fun addTitleSuccess(response: ArrayList<CityVM>, titlePositionMap: ArrayMap<String, Int>) {
        adapter.addData(response)
        this.titlePositionMap = titlePositionMap
        val array = titlePositionMap.keys.toTypedArray()
        sideBar.itemData = array
    }
}
