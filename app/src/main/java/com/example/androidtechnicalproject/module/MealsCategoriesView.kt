package com.example.androidtechnicalproject.module

import android.content.Context
import com.example.androidtechnicalproject.model.MealsCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface MealsCategoriesView {


    fun getLifecycleCoroutineScope(io: CoroutineDispatcher): CoroutineScope
    fun onGetMeals(data: List<MealsCategory>)
    fun getContext() : Context?
    fun onError(message : String)
    fun showLoading()
    fun hideLoading()
    fun onUpdateData(data: MealsCategory)
    fun onDeleteData(data: MealsCategory)

}
