package cn.ygyg.cloudpayment.modular.login.entity

import java.io.Serializable

/**
 * Created by Admin on 2019/4/23.
 *  "mid": 6,
"cellPhone": "15001030281",
"userPassword": null,
"nickName": null,
"userName": null,
"gender": null,
"identityCard": null,
"email": null,
"address": null,
"openId": null,
"appId": null,
"validStatus": null,
"deleted": null,
"signInNum": null,
"createUser": null,
"postDate": "2019-04-19",
"updateUser": null,
"updateDate": null,
"token": null

 */
 class UserEntity : Serializable {
    var cellPhone: String = ""
    var mid: String = ""
    var userPassword: String = ""
    var nickName: String = ""
    var userName: String = ""
    var gender: String = ""
    var identityCard: String = ""
    var email: String = ""
    var address: String = ""
    var appId: String = ""
    var validStatus: String = ""
    var signInNum: String = ""
    var deleted: String = ""
    var createUser: String = ""
    var openId: String = ""
    var postDate: String = ""
    var updateUser: String = ""
    var token: String = ""
}