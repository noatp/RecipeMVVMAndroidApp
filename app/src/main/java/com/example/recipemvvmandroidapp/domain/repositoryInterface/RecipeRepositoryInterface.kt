package com.example.recipemvvmandroidapp.domain.repositoryInterface

import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): Recipe
    fun searchForRecipes(query: String): Flow<PagingData<Recipe>>
}