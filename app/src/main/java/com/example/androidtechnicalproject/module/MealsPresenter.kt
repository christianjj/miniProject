package com.example.androidtechnicalproject.module

import android.util.Log
import com.example.androidtechnicalproject.model.MealResponse
import com.example.androidtechnicalproject.network.ApiService
import com.example.androidtechnicalproject.network.NetworkUtils
import com.example.androidtechnicalproject.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsPresenter(private val mDataView: MealsCategoriesView) {


    private var mApiService: ApiService
  //  private val mContext: Context = mDataView.getContext()!!



    init {
        val client = RetrofitBuilder().build()
        this.mApiService = client.create(ApiService::class.java!!)
    }


    fun getCategories(){
        if(!NetworkUtils.isInternetAvailable(mDataView.getContext())){
            mDataView.onError("Please check your Internet Connection")
            return
        }
        mDataView.showLoading()
        mApiService.getMealCategoriesList()
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    mDataView.hideLoading()
                    if (response.isSuccessful) {
                            val responsess = response.body()
                            Log.d("responsessss", responsess.toString())
                        if (responsess != null) {
                            mDataView.onGetMeals(responsess.categories)
                        }
                    }
                    else {
                            mDataView.onError(response.body()!!.toString())
                        }
                    }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    mDataView.hideLoading()
                    t.printStackTrace()
                    mDataView.onError(t.localizedMessage)
                }
            })

    }
}