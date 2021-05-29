package com.example.recipemvvmandroidapp.ui.router

import androidx.navigation.NavType

enum class ViewDestination(
    val route: String,
    val arguments: Pair<String, NavType<*>>
)
{
    RecipeDetailView(
        route = "recipeDetailView",
        arguments = Pair("recipeId", NavType.IntType)
    )
}