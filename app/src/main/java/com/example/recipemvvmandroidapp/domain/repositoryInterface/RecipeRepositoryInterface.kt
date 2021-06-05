package com.example.recipemvvmandroidapp.domain.repositoryInterface

import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.domain.model.RecipePageDTO

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): RecipeDTO
    suspend fun searchForRecipes(page: Int, query: String): RecipePageDTO
}