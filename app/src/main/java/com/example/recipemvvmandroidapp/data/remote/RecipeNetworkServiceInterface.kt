package com.example.recipemvvmandroidapp.data.remote

import com.example.recipemvvmandroidapp.data.remote.model.Response
import com.example.recipemvvmandroidapp.domain.model.Recipe

interface RecipeNetworkServiceInterface {
    suspend fun getRecipeById(id: Int): Recipe
    suspend fun searchForRecipes(page: Int, query: String): Response
}