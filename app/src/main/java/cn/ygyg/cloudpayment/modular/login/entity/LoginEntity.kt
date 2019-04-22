package cn.ygyg.cloudpayment.modular.login.entity

import java.io.Serializable

/**
 * Created by Admin on 2019/4/22.
 */
data class LoginEntity(
        var cellPhone:String = "",
        var mid:String = "",
        var token:String = ""
):Serializable