package com.example.androidtechnicalproject.fragments

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryListFragmentTest{

    private lateinit var categoryListFragmentTest: CategoryListFragment

    @Before
    fun setUp() {
        categoryListFragmentTest = CategoryListFragment()
    }

    @Test
    fun `empty name returns false`(){
        val result = categoryListFragmentTest.checkInputsIfNotEmpty("","desc", "url")
        assertFalse(result)
    }

    @Test
    fun `complete inputs returns true`(){
        val result =categoryListFragmentTest.checkInputsIfNotEmpty("name","desc", "url")
        assertTrue(result)
    }


    @Test
    fun `empty description inputs returns true`(){
        val result = categoryListFragmentTest.checkInputsIfNotEmpty("chan","", "url")
        assertFalse(result)
    }
}