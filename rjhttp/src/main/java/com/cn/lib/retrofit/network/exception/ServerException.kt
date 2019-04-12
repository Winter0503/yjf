package com.cn.lib.retrofit.network.exception

class ServerException : RuntimeException {
    var code: Int = 0
    var msg: String? = null

    constructor() {}

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, message: String?) : super(message) {
        this.code = code
        this.msg = message
    }
}
