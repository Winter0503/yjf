package cn.ygyg.cloudpayment.utils

import cn.ygyg.cloudpayment.app.Constants
import cn.ygyg.cloudpayment.modular.login.entity.UserEntity

object UserUtil {
    private var userEntity: UserEntity? = null

    fun getUser(): UserEntity? {
        if (userEntity == null) {
            userEntity = SharePreUtil.getBeanByFastJson<UserEntity>(Constants.IntentKey.USER_INFO)
        }
        return userEntity
    }

    fun getUserName(): String {
        return userEntity?.userName ?: ""
    }

    fun getMemberId(): String {
        return userEntity?.mid ?: ""
    }

    fun clear() {
        userEntity = null
    }

}
