package cn.ygyg.cloudpayment.modular.internet.vm

interface CityVM {

    fun cityShowName(): String

    fun cityPinyin(): String

    fun initCityPinyin(pinyin: String)

    fun isRealCity(): Boolean
}