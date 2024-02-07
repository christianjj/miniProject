package com.example.androidtechnicalproject.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtechnicalproject.localdatabase.CategoryDatabase
import com.example.androidtechnicalproject.model.MealsCategory
import com.example.androidtechnicalproject.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<MealsCategory>>
    private val repository: CategoryRepository

    init {
        val userDao = CategoryDatabase.getDatabase(application).categoryDao()
        repository = CategoryRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addCategory(meals: MealsCategory){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(meals)
        }
    }

    fun updateCategory(meals: MealsCategory){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCategory(meals)
        }
    }

    fun deleteCategory(meals: MealsCategory){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(meals)
        }
    }

}