package com.cn.lib.retrofit.network.request

import com.cn.lib.retrofit.network.callback.ResponseCallback
import com.cn.lib.retrofit.network.callback.ResponseClazzCallback
import com.cn.lib.retrofit.network.func.RetryExceptionFunc
import com.cn.lib.retrofit.network.subscriber.RxCallbackSubscriber
import com.cn.lib.retrofit.network.transformer.HandleClazzBodyTransformer
import com.cn.lib.retrofit.network.transformer.HandleErrorTransformer
import com.cn.lib.retrofit.network.transformer.HandleResponseBodyTransformer
import com.cn.lib.retrofit.network.util.RxUtil

import java.lang.reflect.Type

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody

class TemplatePostRequest(url: String) : HttpBodyRequest<TemplatePostRequest>(url) {

    fun <T> execute(type: Type): Observable<T> {
        return build().generateRequest()
                .compose<ResponseBody>(if (isSyncRequest) RxUtil._io_main<ResponseBody>() else RxUtil._main())
                .compose(HandleErrorTransformer<ResponseBody>())
                .retryWhen(RetryExceptionFunc(mRetryCount, mRetryDelay.toLong(), mRetryIncreaseDelay.toLong()))
                .compose(HandleClazzBodyTransformer<T>(type, null))
    }

    fun <T> execute(clazz: Class<T>): Observable<T> {
        return build().generateRequest()
                .compose<ResponseBody>(if (isSyncRequest) RxUtil._io_main() else RxUtil._main())
                .compose(HandleErrorTransformer())
                .retryWhen(RetryExceptionFunc(mRetryCount, mRetryDelay.toLong(), mRetryIncreaseDelay.toLong()))
                .compose(HandleClazzBodyTransformer(clazz, null))
    }

    fun <T> execute(type: Type, callback: ResponseClazzCallback): Observable<T> {
        return build().generateRequest()
                .compose<ResponseBody>(if (isSyncRequest) RxUtil._io_main() else RxUtil._main())
                .compose(HandleErrorTransformer())
                .retryWhen(RetryExceptionFunc(mRetryCount, mRetryDelay.toLong(), mRetryIncreaseDelay.toLong()))
                .compose(HandleClazzBodyTransformer(type, callback))
    }

    fun <T> execute(clazz: Class<T>, callback: ResponseClazzCallback): Observable<T> {
        return build().generateRequest()
                .compose<ResponseBody>(if (isSyncRequest) RxUtil._io_main() else RxUtil._main())
                .compose(HandleErrorTransformer())
                .retryWhen(RetryExceptionFunc(mRetryCount, mRetryDelay.toLong(), mRetryIncreaseDelay.toLong()))
                .compose(HandleClazzBodyTransformer(clazz, callback))
    }

    fun <T> execute(tag: Any, callback: ResponseCallback<T>): Disposable {
        val observable = build().generateObservable(generateRequest())
        return observable.compose(HandleResponseBodyTransformer(callback, tag))
                .compose(HandleErrorTransformer())
                .subscribeWith(RxCallbackSubscriber(mContext, tag, callback))
    }

    private fun generateObservable(observable: Observable<ResponseBody>): Observable<ResponseBody> {
        return observable.compose(if (isSyncRequest) RxUtil._io_main<ResponseBody>() else RxUtil._main())
                .retryWhen(RetryExceptionFunc(mRetryCount, mRetryDelay.toLong(), mRetryIncreaseDelay.toLong()))
    }


}
