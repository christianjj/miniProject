package com.example.androidtechnicalproject.apiTest

import com.example.androidtechnicalproject.model.MealResponse
import com.example.androidtechnicalproject.network.ApiEndPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface FakeApiService {

    @GET(ApiEndPoint.CATEGORIES)
    @Headers("Content-Type: application/json")
    suspend fun getMealCategoriesList() : Response<MealResponse>

}