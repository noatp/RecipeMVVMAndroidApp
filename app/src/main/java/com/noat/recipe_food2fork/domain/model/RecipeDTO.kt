package com.noat.recipe_food2fork.domain.model

data class RecipeDTO(
    val id: Int,
    val title: String,
    val featuredImage: String,
    val ingredients: List<String> = listOf(),
)