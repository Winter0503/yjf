package cn.ygyg.cloudpayment.modular.login.entity

import java.io.Serializable

/**
 * Created by Admin on 2019/4/23.
 */
data class UserEntity(
        var cellPhone: String = "",
        var mid: String = "",
        var postDate: String = ""
) : Serializable