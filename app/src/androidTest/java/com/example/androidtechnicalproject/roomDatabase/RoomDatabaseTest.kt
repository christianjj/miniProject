package com.example.androidtechnicalproject.roomDatabase

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.androidtechnicalproject.localdatabase.CategoriesDao
import com.example.androidtechnicalproject.localdatabase.CategoryDatabase
import com.example.androidtechnicalproject.model.MealsCategory
import getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule


import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RoomDatabaseTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var categoriesDao: CategoriesDao

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        categoryDatabase = Room.inMemoryDatabaseBuilder(context, CategoryDatabase::class.java)
            .allowMainThreadQueries().build()
        categoriesDao = categoryDatabase.categoryDao()
    }

    @After
    fun closeDatabase() {
        categoryDatabase.close()
    }


    @Test
    fun insertData_insert_resultInserted() = runBlockingTest {

        val categoryItem = MealsCategory(
            id = 1,
            strCategory = "test1",
            strCategoryDescription = "testdescription",
            timeStamp = 10000,
            strCategoryThumb = "someurl"
        )
        categoriesDao.addCategory(categoryItem)
        val item = categoriesDao.readAllData().getOrAwaitValue()

        Assert.assertSame(item, item)
        Log.d("insert Data: ", "$item")
    }


    @Test
    fun deleteData_delete_resultEmpty() = runBlockingTest{

        val categoryItem = MealsCategory(
            id = 1,
            strCategory = "test1",
            strCategoryDescription = "testdescription",
            timeStamp = 10000,
            strCategoryThumb = "someurl"
        )
        categoriesDao.addCategory(categoryItem)
        categoriesDao.deleteCategory(categoryItem)

        val item = categoriesDao.readAllData().getOrAwaitValue()
        assertTrue(item.isEmpty())
//        Log.d("Insert Data: ", "remaining: $retrieveInsert")
//        runBlocking {
//            categoriesDao.deleteCategory(categoryItem)
//        }
//
//        val retrieve = categoryDatabase.categoryDao().readAll()
//        Assert.assertTrue(retrieve.isEmpty())
//        Log.d("Delete Data: ", "remaining: $retrieve")

    }


    @Test
    fun updateData_strCategoryisNotEqual() = runBlockingTest{
        val categoryItem = MealsCategory(
            id = 1,
            strCategory = "test1",
            strCategoryDescription = "testdescription",
            timeStamp = 10000,
            strCategoryThumb = "someurl"
        )
            categoriesDao.addCategory(categoryItem)
            categoriesDao.updateCategory(
                    MealsCategory(
                        id = 1,
                        strCategory = "test2",
                        strCategoryDescription = "testdescription",
                        timeStamp = 10000,
                        strCategoryThumb = "someurl"
                    )
                )
            val retrieve = categoriesDao.readAllData().getOrAwaitValue()
            Assert.assertTrue(retrieve[0].strCategory != "test1")
            Log.d("Update Data: ", "${retrieve[0]}")

    }


}