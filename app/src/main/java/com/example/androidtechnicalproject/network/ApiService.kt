package com.example.androidtechnicalproject.network

import com.example.androidtechnicalproject.model.MealResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @GET(ApiEndPoint.CATEGORIES)
   // @Headers("Content-Type: application/json")
   suspend fun getMealCategoriesList(): Response<MealResponse>
}