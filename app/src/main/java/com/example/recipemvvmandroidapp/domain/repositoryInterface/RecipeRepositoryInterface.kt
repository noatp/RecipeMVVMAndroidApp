package com.example.recipemvvmandroidapp.domain.repositoryInterface

import com.example.recipemvvmandroidapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): Recipe
    fun searchForRecipes(query: String): Flow<PagingData<Recipe>>
}