package com.cn.lib.retrofit.network.func

import com.cn.lib.retrofit.network.config.Optional
import com.cn.lib.retrofit.network.entity.ApiResultEntity
import com.cn.lib.retrofit.network.exception.ServerException
import io.reactivex.Observable
import io.reactivex.functions.Function


class HandleResultFunc<T> : Function<ApiResultEntity<T>, Observable<Optional<T>>> {

    @Throws(Exception::class)
    override fun apply(resultEntity: ApiResultEntity<T>): Observable<Optional<T>> {
        if (resultEntity.isOk) {
            return Observable.just(Optional(resultEntity.data))
        }
        throw ServerException(resultEntity.code, resultEntity.msg ?: "请求失败")
    }
}
