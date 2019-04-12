package com.cn.lib.retrofit.network.func

import com.cn.lib.retrofit.network.entity.ApiResultEntity
import com.cn.lib.retrofit.network.exception.ServerException

import io.reactivex.functions.Function

class HandleResultFunc<T> : Function<ApiResultEntity<T>, T> {

    @Throws(Exception::class)
    override fun apply(resultEntity: ApiResultEntity<T>): T? {
        if (resultEntity.isOk) {
            return resultEntity.data
        }
        throw ServerException(resultEntity.code, resultEntity.msg)
    }
}
