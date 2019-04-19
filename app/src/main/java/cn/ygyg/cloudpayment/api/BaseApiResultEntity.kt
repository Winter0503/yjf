package cn.ygyg.cloudpayment.api

import com.cn.lib.retrofit.network.entity.ApiResultEntity

/**
 * Created by Admin on 2019/4/18.
 */
class BaseApiResultEntity<T> : ApiResultEntity<T>() {

    override var code: String = "-1"
        get() = retCode
    override var data: T? = null
        get() = result
    override var msg: String? = ""
        get() = retMsg

    override val isOk: Boolean
        get() = super.isOk

    var retCode: String = "-1"
    var retMsg: String = ""
    var result: T? = null
}
