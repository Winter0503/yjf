package cn.ygyg.cloudpayment.api.request

import cn.ygyg.cloudpayment.api.BaseApiResultEntity
import com.cn.lib.retrofit.network.callback.ResultCallback
import com.cn.lib.retrofit.network.callback.ResultCallbackProxy
import com.cn.lib.retrofit.network.callback.ResultClazzCallProxy
import com.cn.lib.retrofit.network.request.ApiResultPostRequest
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.lang.reflect.Type

/**
 * 根据项目返回结果不同进行返回基类修改
 * Created by Admin on 2019/4/18.
 */
class ResultPostRequest(url: String) : ApiResultPostRequest(url) {

    override fun <T> execute(clazz: Class<T>): Observable<T> {
        return execute(object : ResultClazzCallProxy<BaseApiResultEntity<T>, T>(clazz) {})
    }

    override fun <T> execute(type: Type): Observable<T> {
        return execute(object : ResultClazzCallProxy<BaseApiResultEntity<T>, T>(type) {})
    }

    fun <T> getObservable(proxy: ResultClazzCallProxy<out BaseApiResultEntity<T>, T>): Observable<T> {
        return super.execute(proxy)
    }

    override fun <T> execute(tag: Any, callback: ResultCallback<T>): Disposable {
        return super.execute(tag, object : ResultCallbackProxy<BaseApiResultEntity<T>, T>(callback) {})
    }

    fun <T> executeClazz(tag: Any, proxy: ResultCallbackProxy<out BaseApiResultEntity<T>, T>): Disposable {
        return super.execute(tag, proxy)
    }

}