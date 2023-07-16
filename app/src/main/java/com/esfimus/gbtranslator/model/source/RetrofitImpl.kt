package com.esfimus.gbtranslator.model.source

import com.esfimus.gbtranslator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"

class RetrofitImpl : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> =
        getService(BaseInterceptor.interceptor).search(word)

    private fun getService(interceptor: Interceptor): ApiService =
        createRetrofit(interceptor).create(ApiService::class.java)

    private fun createRetrofit(interceptor: Interceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(createOkHttpClient(interceptor))
            .build()

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }
}