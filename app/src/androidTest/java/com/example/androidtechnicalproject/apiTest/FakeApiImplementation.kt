package com.example.androidtechnicalproject.apiTest

import com.example.androidtechnicalproject.network.ApiEndPoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FakeApiImplementation  {


    fun buildApi() : FakeApiService = Retrofit.Builder()
        .baseUrl(ApiEndPoint.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FakeApiService::class.java)


}