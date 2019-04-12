package com.cn.lib.retrofit.network.interceptor

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(private val tag: Any) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
                .newBuilder()
                .tag(tag)
        return chain.proceed(builder.build())
    }
}
