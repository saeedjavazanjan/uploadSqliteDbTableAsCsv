package com.saeed.android.uploadSqliteDbTableAsCsv.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface RetrofitService {

    @Multipart
    @POST
    suspend fun uploadDatabaseWithAuthorization(
        @Header("Authorization") token: String?,
        @Part dataBase: MultipartBody.Part,
        @Url url: String

    ): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun uploadDatabase(
        @Part dataBase: MultipartBody.Part,
        @Url url: String
    ): Response<ResponseBody>


}