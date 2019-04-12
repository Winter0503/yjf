package com.cn.lib.retrofit.network

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiManager {

    @POST
    fun postBody(@Url url: String, @Body mRequestBody: RequestBody): Observable<ResponseBody>

    @POST
    fun postBody(@Url url: String, @Body obj: Any): Observable<ResponseBody>

    @POST
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun potJsonStr(@Url url: String, @Body jsonBody: RequestBody): Observable<ResponseBody>

    @POST
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun postJson(@Url url: kotlin.String, @Body any: Any): Observable<ResponseBody>

    @POST
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun post(@Url url: kotlin.String, @Body any: Any): Observable<ResponseBody>

    @POST
    fun post(@Url url: String): Observable<ResponseBody>

    @FormUrlEncoded
    @POST
    fun postMap(@Url mUrl: String, @FieldMap(encoded = true) maps: Map<String, String>): Observable<ResponseBody>

    @Multipart
    @POST
    fun uploadFileWithPartList(@Url mUrl: String, @Part partList: List<MultipartBody.Part>): Observable<ResponseBody>

    @Multipart
    @POST
    fun uploadFileWithPartMap(@Url mUrl: String, @PartMap partMap: Map<String, MultipartBody.Part>): Observable<ResponseBody>

    @POST
    fun uploadFileWithBody(@Url url: String, @Body Body: RequestBody): Observable<ResponseBody>

    @Multipart
    @POST
    fun uploadFileWithBodyMap(@Url mUrl: String, @PartMap maps: Map<String, RequestBody>): Observable<ResponseBody>

    @Multipart
    @POST
    fun uploadFileWithBodyMap(@Url mUrl: String, @Body maps: List<RequestBody>): Observable<ResponseBody>

    @Streaming
    @GET
    fun downloadFile(@Url mUrl: String): Observable<ResponseBody>


    //    @POST()
    //    @Headers({"Content-Type: application/json", "Accept: application/json"})
    //    Observable<ResponseBody> postJsonArr(@Url String mUrl, @Body JSONArray jsonArray);
}
