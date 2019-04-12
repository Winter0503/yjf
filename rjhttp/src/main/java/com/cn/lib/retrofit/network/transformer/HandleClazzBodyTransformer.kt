package com.cn.lib.retrofit.network.transformer

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.parser.Feature
import com.cn.lib.retrofit.network.callback.ResponseClazzCallback

import java.lang.reflect.Type

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import okhttp3.ResponseBody

class HandleClazzBodyTransformer<T>(private val type: Type, private val callback: ResponseClazzCallback?) : ObservableTransformer<ResponseBody, T> {

    override fun apply(upstream: Observable<ResponseBody>): ObservableSource<T> {
        return upstream.map { body ->
            if (callback != null) {
                val jsonStr = callback.onTransformationResponse(body)
                JSON.parseObject(jsonStr, type, Feature.UseBigDecimal)
            } else {
                JSON.parseObject<T>(body.string(), type, Feature.UseBigDecimal)
            }
        }
    }

}
