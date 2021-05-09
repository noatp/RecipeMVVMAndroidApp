package com.example.recipemvvmandroidapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(

    @SerialName("pk")
    val pk: Int,

    @SerialName("title")
    val title: String,

    @SerialName("publisher")
    val publisher: String,

    @SerialName("featured_image")
    val featuredImage: String,

    @SerialName("rating")
    val rating: Int = 0,

    @SerialName("source_url")
    val sourceUrl: String,

    @SerialName("ingredients")
    val ingredients: List<String> = emptyList(),

    @SerialName("date_added")
    val dateAdded: String,

    @SerialName("date_updated")
    val dateUpdated: String,
)