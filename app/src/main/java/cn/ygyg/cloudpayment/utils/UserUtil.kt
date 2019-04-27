package cn.ygyg.cloudpayment.utils

import android.text.TextUtils
import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.app.Constants.IntentKey.TOKEN
import cn.ygyg.cloudpayment.modular.login.entity.UserEntity

object UserUtil {
    private var userEntity: UserEntity? = null
    private var token: String? = null

    private fun checkUserEmpty() {
        if (userEntity == null) {
            userEntity = SharePreUtil.getBeanByFastJson<UserEntity>(Constants.IntentKey.USER_INFO)
        }
        userEntity?.token?.apply {
            SharePreUtil.putString(TOKEN, this)
        }
    }

    fun getUser(): UserEntity? {
        checkUserEmpty()
        return userEntity
    }

    fun getUserName(): String {
        checkUserEmpty()
//        return userEntity?.cellPhone ?: "17733696194"
        return "15001030281"
    }

    fun getMemberId(): String {
        checkUserEmpty()
        return userEntity?.mid ?: ""
    }

    fun isLogin(): Boolean {
        checkUserEmpty()
        return !TextUtils.isEmpty(userEntity?.mid)
    }

    fun getToken(): String? {
        if (TextUtils.isEmpty(token)) {
            token = SharePreUtil.getString(TOKEN)
        }
        return token
    }

    fun clear() {
        userEntity = null
    }

}
