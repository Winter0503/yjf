package cn.ygyg.cloudpayment.modular.home.entity

import cn.ygyg.cloudpayment.modular.internet.entity.DeviceResponseEntity
import java.io.Serializable

class DeviceListResponseEntity : Serializable {


    var total: Int = 0
    var list: ArrayList<DeviceResponseEntity>? = null


}
