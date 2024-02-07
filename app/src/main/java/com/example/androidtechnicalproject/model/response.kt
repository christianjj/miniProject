package com.example.androidtechnicalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class MealResponse(
   val categories: List<MealsCategory>
):Serializable


data class Meals(
    val id: Int,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
):Serializable