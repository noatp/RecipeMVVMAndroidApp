package com.example.recipemvvmandroidapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Recipe (
    @PrimaryKey
    @SerialName(value = "pk")
    val id: Int,

    val title: String,

    val publisher: String,

    @SerialName(value = "featured_image")
    val featuredImage: String,

    val rating: Int,

    @SerialName(value = "source_url")
    val sourceUrl: String,

    val ingredients: List<String> = listOf(),

    @SerialName(value = "date_added")
    val dateAdded: String,

    @SerialName(value = "date_updated")
    val dateUpdated: String,
)