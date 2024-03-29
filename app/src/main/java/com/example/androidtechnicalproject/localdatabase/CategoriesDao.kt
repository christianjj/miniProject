package com.example.androidtechnicalproject.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidtechnicalproject.model.MealsCategory


@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(meals: MealsCategory)
    @Query("SELECT * FROM local_category ORDER BY id ASC")
    fun readAllData(): LiveData<List<MealsCategory>>

    @Query("SELECT * FROM local_category ORDER BY id ASC")
    fun readAll(): List<MealsCategory>

    @Query("SELECT * FROM local_category ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestCategory(): MealsCategory?

    @Update()
    suspend fun updateCategory(meals: MealsCategory)

    @Delete()
    suspend fun deleteCategory(meals: MealsCategory)



}