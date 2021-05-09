package com.example.recipemvvmandroidapp.router

import androidx.navigation.NavType

enum class ViewDestination(
    val route: String,
    val arguments: List<Pair<String, NavType<*>>>
)
{
    RecipeDetailView(
        route = "recipeDetailView",
        arguments = listOf(Pair("recipeId", NavType.IntType))
    )
}