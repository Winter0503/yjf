package cn.ygyg.cloudpayment.modular.login.entity

import java.io.Serializable

data class TokenEntity(
        var access_token: String = "",
        var refresh_token: String = "",
        var unionid: String = "",
        var scope: String = "",
        var expires_in: String = "",
        var openid: String = ""
):Serializable