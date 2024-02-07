package com.example.androidtechnicalproject

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidtechnicalproject.base.BaseActivity
import com.example.androidtechnicalproject.databinding.ActivityMainBinding
import com.example.androidtechnicalproject.fragments.CategoryListFragment

class MainActivity : BaseActivity() {



    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: ConstraintLayout = binding.root
        setContentView(view)

        addFragment(CategoryListFragment())
    }



}