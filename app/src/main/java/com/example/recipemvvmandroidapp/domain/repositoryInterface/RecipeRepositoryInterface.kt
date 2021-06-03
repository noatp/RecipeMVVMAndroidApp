package com.example.recipemvvmandroidapp.domain.repositoryInterface

import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import kotlinx.coroutines.flow.Flow

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): RecipeDTO
    suspend fun searchForRecipes(page: Int, query: String): List<RecipeDTO>
}