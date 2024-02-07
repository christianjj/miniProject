package com.example.androidtechnicalproject.module

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.androidtechnicalproject.R
import com.example.androidtechnicalproject.databinding.MealsItemBinding
import com.example.androidtechnicalproject.model.MealsCategory



class MealsAdapter(val mView: MealsCategoriesView) : RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {

    private var mMeals = listOf<MealsCategory>()

    fun initialize(meals: List<MealsCategory>){
       // this.mMeals.clear()
        this.mMeals = meals
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MealsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MealsViewHolder(MealsItemBinding.inflate(inflater, parent,false)
        )
    }

    override fun getItemCount(): Int {
        return mMeals.size
    }

    override fun onBindViewHolder(p0: MealsViewHolder, position: Int) {
        p0.bind(mMeals[position], mView, position)
    }

    class MealsViewHolder(val binding: MealsItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(m: MealsCategory, view: MealsCategoriesView, position: Int){
            binding.containerMeals.setOnClickListener {
                view.onUpdateData(m)
            }

            binding.containerMeals.setOnLongClickListener {
                view.onDeleteData(m)
                true
            }

            binding.tvTitle.text = m.strCategory
            binding.tvDescription.text = m.strCategoryDescription
            binding.ivPic.load(m.strCategoryThumb) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }

        }
    }
}