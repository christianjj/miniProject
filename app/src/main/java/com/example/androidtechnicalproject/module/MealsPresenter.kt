package com.example.androidtechnicalproject.module

import android.util.Log
import com.example.androidtechnicalproject.localdatabase.CategoriesDao
import com.example.androidtechnicalproject.network.ApiEndPoint
import com.example.androidtechnicalproject.network.ApiService
import com.example.androidtechnicalproject.network.NetworkUtils
import com.example.androidtechnicalproject.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class MealsPresenter(private val mDataView: MealsCategoriesView, private val categoriesDao: CategoriesDao) {

    private val fiveMinutesAgo = System.currentTimeMillis() - ApiEndPoint.CACHE

    private var mApiService: ApiService

  //  private val mContext: Context = mDataView.getContext()!!
    var stopCallingGetCategories : Job? = null


    init {
        val client = RetrofitBuilder().build()
        this.mApiService = client.create(ApiService::class.java!!)
    }


     fun getCategories(){
        if(!NetworkUtils.isInternetAvailable(mDataView.getContext())){
            mDataView.onError("Please check your Internet Connection")
            return
        }
        //mDataView.showLoading()



        stopCallingGetCategories =  mDataView.getLifecycleCoroutineScope(Dispatchers.IO).launch {

            //categoriesApiCall()

            val dbLatestresult = categoriesDao.getLatestCategory()



            if (dbLatestresult != null) {
                if (!isDataExpired(dbLatestresult.timeStamp)) {
                    Log.d("MealsPresenter", "data not is expired ")
                    val dbReadAllCategories = categoriesDao.readAll()
                    withContext(Dispatchers.Main) {
                        mDataView.onGetMeals(dbReadAllCategories)
                    }
                }
                else{
                    Log.d("MealsPresenter", "data is expired")
                    withContext(Dispatchers.Main) {
                        mDataView.showLoading()
                    }
                    categoriesApiCall()

                }
            }
            else {
                categoriesApiCall()
            }


        }

//        mApiService.getMealCategoriesList()
//            .enqueue(object : Callback<MealResponse> {
//                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
//                    mDataView.hideLoading()
//                    if (response.isSuccessful) {
//                            val responsess = response.body()
//                            Log.d("responsessss", responsess.toString())
//                        if (responsess != null) {
//                            mDataView.onGetMeals(responsess.categories)
//                        }
//                    }
//                    else {
//                            mDataView.onError(response.body()!!.toString())
//                        }
//                    }
//
//                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
//                    mDataView.hideLoading()
//                    t.printStackTrace()
//                    mDataView.onError(t.localizedMessage)
//                }
//            })

    }

    private fun isDataExpired(timestamp: Long): Boolean {
        val currentTime = Calendar.getInstance().timeInMillis
        val dataExpirationTime = timestamp + 5000 // 5 seconds
        Log.d("MealsPresenter", "$currentTime > $dataExpirationTime")
        return currentTime > dataExpirationTime


    }

    suspend fun categoriesApiCall(){
        val response = mApiService.getMealCategoriesList()
        if (response.isSuccessful){
            withContext(Dispatchers.Main) {
                mDataView.onGetMeals(response.body()!!.categories)
                mDataView.hideLoading()
            }
        }
        else
        {
            withContext(Dispatchers.Main) {
                mDataView.onError(response.message())
                mDataView.hideLoading()
            }
        }
    }


    fun cancelFetch(){
        stopCallingGetCategories?.cancel()
    }
}