package com.example.recipemvvmandroidapp.domain.model

data class RecipePageDTO(
    val recipeList: List<RecipeDTO>,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
)