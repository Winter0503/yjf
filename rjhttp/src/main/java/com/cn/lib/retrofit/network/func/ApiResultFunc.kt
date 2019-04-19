package com.cn.lib.retrofit.network.func

import android.text.TextUtils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.parser.Feature
import com.cn.lib.retrofit.network.entity.ApiResultEntity

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

import io.reactivex.functions.Function
import okhttp3.ResponseBody

class ApiResultFunc<T>(private val type: Type) : Function<ResponseBody, ApiResultEntity<T>> {

    @Throws(Exception::class)
    override fun apply(body: ResponseBody): ApiResultEntity<T> {
        var apiResult = ApiResultEntity<T>()
        val jsonStr = body.string()
        apiResult.code = "-1"
        if (TextUtils.isEmpty(jsonStr))
            throw NullPointerException("json is null")
        try {
            val subClazz = (type as ParameterizedType).rawType as Class<*>
            if (ApiResultEntity::class.java.isAssignableFrom(subClazz)) {
                apiResult = JSON.parseObject(jsonStr, type, Feature.UseBigDecimal)
            } else {
                apiResult.code = "-1"
                apiResult.msg = "ApiResultEntity.class.isAssignableFrom(subClazz) err!!"
            }
        } finally {
            body.close()
        }
        return apiResult
    }
}
