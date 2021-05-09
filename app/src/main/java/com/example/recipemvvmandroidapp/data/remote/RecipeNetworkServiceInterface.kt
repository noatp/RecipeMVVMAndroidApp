package com.example.recipemvvmandroidapp.data.remote

import com.example.recipemvvmandroidapp.data.remote.model.RecipeDTO

interface RecipeNetworkServiceInterface {
    suspend fun getRecipeById(id: Int): RecipeDTO
    suspend fun searchForRecipes(page: Int, query: String): List<RecipeDTO>
}