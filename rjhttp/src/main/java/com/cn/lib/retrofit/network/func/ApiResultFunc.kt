package com.cn.lib.retrofit.network.func

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.parser.Feature
import com.cn.lib.retrofit.network.entity.ApiResultEntity
import com.cn.lib.retrofit.network.util.Util
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class ApiResultFunc<T>(private val type: Type) : Function<ResponseBody, ApiResultEntity<T>> {

    @Throws(Exception::class)
    override fun apply(body: ResponseBody): ApiResultEntity<T> {
        var apiResult = ApiResultEntity<T>()
        val jsonStr = body.string()
        apiResult.code = "-1"
        try {
            val subClazz = (type as ParameterizedType).rawType as Class<*>
            if (ApiResultEntity::class.java.isAssignableFrom(subClazz)) {
                apiResult = JSON.parseObject(jsonStr, type, Feature.UseBigDecimal)
                //规避如果data如果是null时RxJava中的Map操作符报错的问题
                if (apiResult.data == null) {
                    val clazz = Util.getGenericClass(type, 0)
                    apiResult.data = clazz.newInstance() as T?
                }
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
