package com.noat.recipe_food2fork.domain.model

data class RecipePageDTO(
    val recipeList: List<RecipeDTO>,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
)