package com.cn.lib.retrofit.network.func

import android.os.Build
import android.support.annotation.RequiresApi
import com.cn.lib.retrofit.network.entity.ApiResultEntity
import com.cn.lib.retrofit.network.exception.ServerException
import io.reactivex.Observable
import io.reactivex.functions.Function
import com.cn.lib.retrofit.network.config.Optional


class HandleResultFunc<T> : Function<ApiResultEntity<T>, Observable<Optional<T>>> {

    @RequiresApi(Build.VERSION_CODES.N)
    @Throws(Exception::class)
    override fun apply(resultEntity: ApiResultEntity<T>): Observable<Optional<T>> {
        if (resultEntity.isOk) {
            return Observable.just(Optional(resultEntity.data))
        }
        throw ServerException(resultEntity.code, resultEntity.msg)
    }
}
