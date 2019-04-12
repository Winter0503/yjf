package com.cn.lib.retrofit.network.exception

class ApiThrowable : Exception {
    var code: Int = -1
     override var message: String? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(throwable: Throwable, code: Int, message: String) : super(throwable) {
        this.code = code
        this.message = message
    }
}
