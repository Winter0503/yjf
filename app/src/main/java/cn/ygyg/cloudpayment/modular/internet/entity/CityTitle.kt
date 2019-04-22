package cn.ygyg.cloudpayment.modular.internet.entity

import cn.ygyg.cloudpayment.modular.internet.vm.CityVM

class CityTitle : CityVM {
    var cityTitle: String? = null
    var cityPinyin: String? = null

    override fun cityShowName(): String {
        return if (cityTitle.isNullOrEmpty()) "" else cityTitle!!
    }

    override fun cityPinyin(): String {
        return if (cityPinyin.isNullOrEmpty()) "" else cityPinyin!!
    }

    override fun initCityPinyin(pinyin: String) {
        this.cityPinyin = pinyin
    }

    override fun isRealCity(): Boolean {
        return false
    }
}
