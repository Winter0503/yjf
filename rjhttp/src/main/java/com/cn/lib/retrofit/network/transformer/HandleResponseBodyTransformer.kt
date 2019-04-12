package com.cn.lib.retrofit.network.transformer

import com.cn.lib.retrofit.network.callback.ResponseCallback

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import okhttp3.ResponseBody

class HandleResponseBodyTransformer<T>(private val callback: ResponseCallback<T>, private val mTag: Any) : ObservableTransformer<ResponseBody, T> {

    override fun apply(upstream: Observable<ResponseBody>): ObservableSource<T> {
        return upstream.map { body -> callback.onTransformationResponse(body) }
    }
}
