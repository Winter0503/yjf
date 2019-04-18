package cn.ygyg.cloudpayment.api

import cn.ygyg.cloudpayment.api.request.ResultDeleteRequest
import cn.ygyg.cloudpayment.api.request.ResultGetRequest
import cn.ygyg.cloudpayment.api.request.ResultPostRequest
import cn.ygyg.cloudpayment.api.request.ResultPutRequest

/**
 * 请求管理类
 * Created by Admin on 2019/4.
 */
object RequestManager {
    fun post(url: String): ResultPostRequest {
        return ResultPostRequest(url)
    }

    fun delete(url: String): ResultDeleteRequest {
        return ResultDeleteRequest(url)
    }

    fun put(url: String): ResultPutRequest {
        return ResultPutRequest(url)
    }

    fun get(url: String): ResultGetRequest {
        return ResultGetRequest(url)
    }
}