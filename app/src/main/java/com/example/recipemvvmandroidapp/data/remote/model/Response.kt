package com.example.recipemvvmandroidapp.data.remote.model

import com.example.recipemvvmandroidapp.domain.model.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Recipe>,
)