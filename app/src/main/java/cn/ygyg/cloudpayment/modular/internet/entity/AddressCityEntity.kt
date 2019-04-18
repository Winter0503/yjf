package cn.ygyg.cloudpayment.modular.internet.entity

class AddressCityEntity : CityVM {
    var address: String? = null

    override fun getCityName(): String {
        return if (address.isNullOrEmpty()) "" else address!!
    }

}
