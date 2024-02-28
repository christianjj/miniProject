package com.example.androidtechnicalproject.apiTest

import android.util.Log
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class TestApiIsolation: TestCase() {

    @Test
    fun testApi_check_response_resultSuccess(){
        val api = FakeApiImplementation.buildApi()
        val test = runBlocking {
            api.getMealCategoriesList()
        }
        assertEquals(test.isSuccessful, true)
    }


    @Test
    fun testApi_getData_resultNotEmpty(){
        val api = FakeApiImplementation.buildApi()
        val test = runBlocking {
            api.getMealCategoriesList()
        }
        assertNotNull(test.body().toString())
        Log.d("list",test.body().toString() )
    }
}