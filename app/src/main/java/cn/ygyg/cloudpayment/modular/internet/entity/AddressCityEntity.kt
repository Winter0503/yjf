package cn.ygyg.cloudpayment.modular.internet.entity

class AddressCityEntity : CityVM {


    var cityName: String? = null

    var cityPinyin: String? = null

    var cityId: String? = null

    override fun cityShowName(): String {
        return if (cityName.isNullOrEmpty()) "" else cityName!!
    }

    override fun isRealCity(): Boolean {
        return !cityPinyin.isNullOrEmpty()
    }

}
