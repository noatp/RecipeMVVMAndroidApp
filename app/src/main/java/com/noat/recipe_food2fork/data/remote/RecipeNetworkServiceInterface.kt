package com.noat.recipe_food2fork.data.remote

import com.noat.recipe_food2fork.data.SearchResponse
import com.noat.recipe_food2fork.domain.model.Recipe

interface RecipeNetworkServiceInterface {
    suspend fun getRecipeById(id: Int): Recipe
    suspend fun searchForRecipes(page: Int, query: String): SearchResponse
}