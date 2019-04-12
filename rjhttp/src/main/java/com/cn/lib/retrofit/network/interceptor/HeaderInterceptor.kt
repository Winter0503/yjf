package com.cn.lib.retrofit.network.interceptor

import com.cn.lib.retrofit.network.util.LogUtil

import java.io.IOException
import java.util.HashMap

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private var map: MutableMap<String, String>) : Interceptor {

    fun addHeaderMap(map: Map<String, String>) {
        if (this.map == null) {
            this.map = HashMap()
        }
        this.map!!.putAll(map)
    }

    fun addHeader(key: String, value: String) {
        this.map!![key] = value
    }

    fun clearAll() {
        if (this.map != null) {
            this.map!!.clear()
        }
    }

    fun remove(key: String) {
        if (this.map != null && map!!.containsKey(key)) {
            this.map!!.remove(key)
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (map != null && map!!.size > 0) {
            val keys = map!!.keys
            for (headerKey in keys) {
                builder.addHeader(headerKey, if (map!![headerKey] == null) "" else map!![headerKey]).build()
            }
        }
        LogUtil.i("RxHttp", "-->>headers：" + builder.build().headers().toString())
        return chain.proceed(builder.build())
    }
}
