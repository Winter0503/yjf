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
        apiResult.code = -1
        if (TextUtils.isEmpty(jsonStr))
            throw NullPointerException("json is null")
        try {
            val subClazz = (type as ParameterizedType).rawType as Class<*>
            if (ApiResultEntity::class.java.isAssignableFrom(subClazz)) {
                apiResult = JSON.parseObject(jsonStr, type, Feature.UseBigDecimal)
            } else {
                apiResult.code = -1
                apiResult.msg = "ApiResultEntity.class.isAssignableFrom(subClazz) err!!"
            }
            //            if (type instanceof ParameterizedType) {//自定义的ApiResult继承自com.retrofit.network.entity.ApiResultEntity<T>
            //                final Class<T> cls = (Class) ((ParameterizedType) type).getRawType();
            //                if (ApiResultEntity.class.isAssignableFrom(cls)) { //判断传入的是否是ApiResultEntity或者其子类
            //                    final Type[] params = ((ParameterizedType) type).getActualTypeArguments();
            //                    final Class clazz = Util.getClass(params[0], 0);
            //                    final Class rawType = Util.getClass(type, 0);
            //                    if (!List.class.isAssignableFrom(rawType) && clazz.equals(String.class)) {
            //                        ApiResultEntity<String> resultEntity = JSON.parseObject(jsonStr, type, Feature.UseBigDecimal);
            //                        apiResult.setData((T) resultEntity.getData());
            //                        apiResult.setCode(resultEntity.getCode());
            //                        apiResult.setMsg(resultEntity.getMsg());
            //                    } else {
            //                        apiResult = JSON.parseObject(jsonStr, type, Feature.UseBigDecimal);
            //                    }
            //                } else {
            //                    throw new ServerException(-1, "传入的泛型不正确，应该继承自AipResultEntity");
            //                }
            //
            //            } else {
            //                final Class<T> clazz = Util.getClass(type, 0);
            //                if (clazz.equals(String.class)) {
            //                    ApiResultEntity<String> resultEntity = JSON.parseObject(jsonStr, type, Feature.UseBigDecimal);
            //                    apiResult.setData((T) resultEntity.getData());
            //                    apiResult.setCode(resultEntity.getCode());
            //                    apiResult.setMsg(resultEntity.getMsg());
            //                } else {
            //                    apiResult = JSON.parseObject(jsonStr, type, Feature.UseBigDecimal);
            //                }
            //            }
        } finally {
            body.close()
        }
        return apiResult
    }
}
