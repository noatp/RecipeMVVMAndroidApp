package com.noat.recipe_food2fork.data.remote.model

import com.noat.recipe_food2fork.domain.model.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Recipe>,
)