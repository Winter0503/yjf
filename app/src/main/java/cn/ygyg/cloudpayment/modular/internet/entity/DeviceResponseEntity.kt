package cn.ygyg.cloudpayment.modular.internet.entity

import cn.ygyg.cloudpayment.modular.internet.vm.DeviceVM
import cn.ygyg.cloudpayment.utils.UserUtil
import java.io.Serializable

class DeviceResponseEntity : Serializable, DeviceVM {
    var appId: String? = null
    var cityBusinessType: String? = null
    var companyCode: String? = null
    var companyName: String? = null
    var contractBalance: String? = null
    var contractId: String? = null
    var contractNo: String? = null
    var contractType: String? = null
    var createUser: String? = null
    var customerCode: String? = null
    var customerCredentialNo: String? = null
    var customerName: String? = null
    var customerTel: String? = null
    var housePropertyCode: String? = null
    var housePropertyName: String? = null
    var locked: Boolean? = null
    var memberNo: String? = null
    var meterNo: String? = null
    var mid: Int? = null
    var openId: String? = null
    var postDate: String? = null
    var remark: String? = null
    var updateDate: String? = null
    var updateUser: String? = null


    override fun userName(): String {
        return if (customerName.isNullOrEmpty()) "" else customerName!!
    }

    override fun deviceCompany(): String {
        return if (companyName.isNullOrEmpty()) "" else companyName!!
    }

    override fun deviceCode(): String {
        return if (meterNo.isNullOrEmpty()) "" else meterNo!!
    }

    override fun deviceAddress(): String {
        return if (housePropertyName.isNullOrEmpty()) "" else housePropertyName!!
    }

    override fun deviceBalance(): String {
        return if (contractBalance.isNullOrEmpty()) "" else contractBalance!! + "元"
    }

    override fun companyCode(): String {
        return if (companyCode.isNullOrEmpty()) "" else companyCode!!
    }
}