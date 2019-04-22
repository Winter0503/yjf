package cn.ygyg.cloudpayment.modular.internet.presenter

import android.util.ArrayMap
import cn.ygyg.cloudpayment.api.RequestManager
import cn.ygyg.cloudpayment.api.UrlConstants
import cn.ygyg.cloudpayment.modular.internet.contract.AddressSelectorActivityContract
import cn.ygyg.cloudpayment.modular.internet.entity.CityListResponseEntity
import cn.ygyg.cloudpayment.modular.internet.entity.CityTitle
import cn.ygyg.cloudpayment.modular.internet.vm.CityVM
import cn.ygyg.cloudpayment.utils.ProgressUtil
import com.cn.lib.basic.BasePresenterImpl
import com.cn.lib.retrofit.network.callback.ResultCallback
import com.cn.lib.retrofit.network.exception.ApiThrowable
import com.github.promeg.pinyinhelper.Pinyin
import java.util.*
import kotlin.collections.ArrayList

class AddressSelectorActivityPresenter(view: AddressSelectorActivityContract.View) :
        BasePresenterImpl<AddressSelectorActivityContract.View>(view),
        AddressSelectorActivityContract.Presenter {
    override fun loadCityList() {
//        return
        RequestManager.get(UrlConstants.cityList)
                .param("pageSize", "20")
                .execute("cityList", object : ResultCallback<CityListResponseEntity>() {
                    override fun onStart(tag: Any?) {
//                        mvpView?.getViewContext()?.let {
//                            ProgressUtil.showProgressDialog(it, "")
//                        }
                    }

                    override fun onCompleted(tag: Any?) {
//                        ProgressUtil.dismissProgressDialog()
                    }

                    override fun onError(tag: Any?, e: ApiThrowable) {
                        e.message?.let { mvpView?.showToast(it) }
                    }

                    override fun onSuccess(tag: Any?, t: CityListResponseEntity?) {
                        t?.list?.let {
                            mvpView?.onLoadCityListSuccess(getSortPinyinCityList(it))
                        }
                    }
                })
    }

    override fun addTitleItem(response: ArrayList<out CityVM>) {
        val result = ArrayList<CityVM>()
        var char = '0'
        val titlePositionMap = ArrayMap<String, Int>()
        for (ele in response) {
            val pinyin = ele.cityPinyin()
            val c = pinyin[0]
            if (c != char) {
                titlePositionMap[c.toString()] = result.size
                result.add(CityTitle().apply { cityTitle = c.toString() })
            }
            result.add(ele)
            char = c
        }
        mvpView?.addTitleSuccess(result, titlePositionMap)
    }

    override fun getCompanyByCity(city: CityVM) {

    }

    private fun getSortPinyinCityList(response: ArrayList<out CityVM>): ArrayList<CityVM> {
        val map = TreeMap<String, CityVM>()
        for (ele in response) {
            val pinyin = Pinyin.toPinyin(ele.cityShowName(), "")
            ele.initCityPinyin(pinyin)
            map[pinyin] = ele
        }
        return ArrayList(map.values)
    }

}
