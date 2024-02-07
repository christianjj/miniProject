package com.example.androidtechnicalproject.repository

import androidx.lifecycle.LiveData
import com.example.androidtechnicalproject.localdatabase.CategoriesDao
import com.example.androidtechnicalproject.model.MealsCategory

class CategoryRepository(private val categoryDao: CategoriesDao) {

    val readAllData: LiveData<List<MealsCategory>> = categoryDao.readAllData()

   suspend fun addCategory(category: MealsCategory){
        categoryDao.addCategory(category)
    }

    suspend fun updateCategory(category: MealsCategory){
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: MealsCategory){
        categoryDao.deleteCategory(category)
    }
}