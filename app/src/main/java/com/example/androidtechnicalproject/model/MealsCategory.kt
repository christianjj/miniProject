package com.example.androidtechnicalproject.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "local_category", indices = [Index(value = ["strCategory"], unique = true) ])
data class MealsCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val strCategory: String,
    val strCategoryDescription: String,
    val timeStamp: Long,
    val strCategoryThumb: String
):Parcelable