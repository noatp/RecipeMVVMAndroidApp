package com.noat.recipe_food2fork.data

import com.noat.recipe_food2fork.domain.model.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Recipe>,
){
    companion object{
        val empty = SearchResponse(
            count = 0,
            next = null,
            previous = null,
            results = listOf()
        )
    }
}

