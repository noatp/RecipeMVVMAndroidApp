package com.example.recipemvvmandroidapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
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