package cn.ygyg.cloudpayment.modular.login.entity

import java.io.Serializable

class  TokenEntity:Serializable {
    var access_token: String = ""
    var refresh_token: String = ""
    var unionid: String = ""
    var scope: String = ""
    var expires_in: String = ""
    var openid: String = ""
}