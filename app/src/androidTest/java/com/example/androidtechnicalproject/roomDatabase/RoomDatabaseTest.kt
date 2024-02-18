package com.example.androidtechnicalproject.roomDatabase

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.androidtechnicalproject.localdatabase.CategoriesDao
import com.example.androidtechnicalproject.localdatabase.CategoryDatabase
import com.example.androidtechnicalproject.model.MealsCategory

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


import junit.framework.TestCase


import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest: TestCase() {

    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var categoriesDao : CategoriesDao
    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        categoryDatabase = Room.inMemoryDatabaseBuilder(context, CategoryDatabase::class.java).build()
        categoriesDao = categoryDatabase.categoryDao()
    }

    @After
    fun closeDatabase(){
        categoryDatabase.close()
    }

    @Test
     fun insertData_insert_resultInserted(){
        val categoryItem = MealsCategory(id = 1, strCategory = "test1", strCategoryDescription = "testdescription",  timeStamp = 10000, strCategoryThumb = "someurl")
         runBlocking {
             categoriesDao.addCategory(categoryItem)
             val retrieve = categoryDatabase.categoryDao().readAll()
             Assert.assertEquals(retrieve.isNotEmpty(), true)

             Log.d("insert Data: ", "$retrieve")
         }
    }

    @Test
     fun deleteData_delete_resultEmpty(){
        val categoryItem = MealsCategory(id = 1, strCategory = "test1", strCategoryDescription = "testdescription", timeStamp = 10000, strCategoryThumb = "someurl")
        runBlocking {
            categoriesDao.addCategory(categoryItem)

            val retrieveInsert = categoryDatabase.categoryDao().readAll()
            Assert.assertTrue(retrieveInsert.size == 1)
            Log.d("Insert Data: ", "remaining: $retrieveInsert")
            runBlocking {
                categoriesDao.deleteCategory(categoryItem)
            }

            val retrieve = categoryDatabase.categoryDao().readAll()
            Assert.assertTrue(retrieve.isEmpty())
            Log.d("Delete Data: ", "remaining: $retrieve")
        }
    }


    @Test
     fun updateData_update_resultUpdatedStrCategory(){
        val categoryItem = MealsCategory(id = 1, strCategory = "test1", strCategoryDescription = "testdescription", timeStamp = 10000, strCategoryThumb = "someurl")
        runBlocking {
            categoriesDao.addCategory(categoryItem)

            val retrieveInsert = categoryDatabase.categoryDao().readAll()
            Assert.assertTrue(retrieveInsert.size == 1)
            Log.d("Insert Data: ", "remaining: $retrieveInsert")


            runBlocking {
                categoriesDao.updateCategory(
                    MealsCategory(
                        id = 1,
                        strCategory = "test2",
                        strCategoryDescription = "testdescription",
                        timeStamp = 10000,
                        strCategoryThumb = "someurl"
                    )
                )
            }

            val retrieve = categoryDatabase.categoryDao().readAll()
            Assert.assertTrue(retrieve[0].strCategory == "test2")
            Log.d("Update Data: ", "${retrieve[0]}")
        }
    }



}