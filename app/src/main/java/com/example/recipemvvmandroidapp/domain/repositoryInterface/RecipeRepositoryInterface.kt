package com.example.recipemvvmandroidapp.domain.repositoryInterface

import com.example.recipemvvmandroidapp.domain.model.Recipe

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): Recipe
    suspend fun searchForRecipes(page: Int, query: String): List<Recipe>
}