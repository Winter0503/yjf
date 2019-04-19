package cn.ygyg.cloudpayment.modular.internet.presenter

import android.util.ArrayMap
import cn.ygyg.cloudpayment.modular.internet.contract.AddressSelectorActivityContract
import cn.ygyg.cloudpayment.modular.internet.entity.AddressCityEntity
import cn.ygyg.cloudpayment.modular.internet.entity.CityVM
import com.cn.lib.basic.BasePresenterImpl
import com.github.promeg.pinyinhelper.Pinyin
import java.util.*
import kotlin.collections.ArrayList

class AddressSelectorActivityPresenter(view: AddressSelectorActivityContract.View) :
        BasePresenterImpl<AddressSelectorActivityContract.View>(view),
        AddressSelectorActivityContract.Presenter {
    override fun loadCityList() {
        var response = ArrayList<AddressCityEntity>()
        response.add(AddressCityEntity().apply { cityName = "北京" })
        response.add(AddressCityEntity().apply { cityName = "石家庄市" })
        response.add(AddressCityEntity().apply { cityName = "邯郸市" })
        response.add(AddressCityEntity().apply { cityName = "重庆" })
        response.add(AddressCityEntity().apply { cityName = "张家口市" })
        response.add(AddressCityEntity().apply { cityName = "泊头市" })
        response.add(AddressCityEntity().apply { cityName = "任丘市" })
        response.add(AddressCityEntity().apply { cityName = "黄骅市" })
        response.add(AddressCityEntity().apply { cityName = "潞城市" })
        response.add(AddressCityEntity().apply { cityName = "巴彦淖尔市" })
        response = getSortPinyinCityList(response)
        mvpView?.onLoadCityListSuccess(response)
    }

    override fun addTitleItem(response: ArrayList<out CityVM>) {
        val result = ArrayList<CityVM>()
        var char = '0'
        val titlePositionMap = ArrayMap<String, Int>()
        for (ele in response) {
            val pinyin = ele.cityPinyin()
            pinyin?.let {
                val c = it[0]
                if (c != char) {
                    titlePositionMap[c.toString()] = result.size
                    result.add(AddressCityEntity().apply { cityName = c.toString() })
                }
                result.add(ele)
                char = c
            }
        }
        mvpView?.addTitleSuccess(result, titlePositionMap)
    }

    private fun getSortPinyinCityList(response: ArrayList<AddressCityEntity>): ArrayList<AddressCityEntity> {
        val map = TreeMap<String, AddressCityEntity>()
        for (ele in response) {
            val pinyin = Pinyin.toPinyin(ele.cityShowName(), "")
            ele.cityPinyin = pinyin
            map[pinyin] = ele
        }
        return ArrayList(map.values)
    }

}
