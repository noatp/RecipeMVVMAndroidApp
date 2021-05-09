package com.example.recipemvvmandroidapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(

    @SerialName("count")
    val count: Int,

    @SerialName("results")
    val results: List<RecipeDTO>,
)