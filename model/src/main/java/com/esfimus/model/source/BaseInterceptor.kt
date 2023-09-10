package com.esfimus.model.source

import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor private constructor() : Interceptor {

    private var responseCode: Int = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        responseCode = response.code
        return response
    }

    fun getResponseCode(): ServerResponseStatusCode {
        var statusCode = ServerResponseStatusCode.UNDEFINED_ERROR
        when (responseCode) {
            100 -> statusCode = ServerResponseStatusCode.INFO
            200 -> statusCode = ServerResponseStatusCode.SUCCESS
            300 -> statusCode = ServerResponseStatusCode.REDIRECTION
            400 -> statusCode = ServerResponseStatusCode.CLIENT_ERROR
            500 -> statusCode = ServerResponseStatusCode.SERVER_ERROR
        }
        return statusCode
    }

    enum class ServerResponseStatusCode {
        INFO,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNDEFINED_ERROR
    }

    companion object {
        val interceptor: BaseInterceptor
            get() = BaseInterceptor()
    }

}