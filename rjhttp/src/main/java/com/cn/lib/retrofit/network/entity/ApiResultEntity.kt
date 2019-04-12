package com.cn.lib.retrofit.network.entity

open class ApiResultEntity<T> {
    open var code: Int = -1
    open var data: T? = null
    open var msg: String? = null

    open val isOk: Boolean
        get() = code == 0
}
