package com.example.recipemvvmandroidapp.domain.repositoryInterface

import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import kotlinx.coroutines.flow.Flow

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): RecipeDTO
    fun searchForRecipes(query: String): Flow<PagingData<RecipeDTO>>
}