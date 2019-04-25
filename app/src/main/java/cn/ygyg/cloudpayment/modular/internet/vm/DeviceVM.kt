package cn.ygyg.cloudpayment.modular.internet.vm

interface DeviceVM {

    fun userName(): String
    fun deviceCompany(): String
    fun deviceCode(): String
    fun deviceAddress(): String
    fun deviceBalance(): String
    fun companyCode(): String

}