package com.example.androidtechnicalproject.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitBuilder {
    fun build(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(LoggingInterceptor()).build()

        val gson = GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .setDateFormat("dd/MM/yyyy")
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(ApiEndPoint.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }




    internal class LoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            val responseString = String(response.body!!.bytes())
            return response.newBuilder()
                .body(ResponseBody.create(response.body!!.contentType(), responseString))
                .build()
        }
    }


}