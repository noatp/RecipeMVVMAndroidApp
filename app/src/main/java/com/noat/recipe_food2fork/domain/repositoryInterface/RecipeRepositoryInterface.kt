package com.noat.recipe_food2fork.domain.repositoryInterface

import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.domain.model.RecipePageDTO
import kotlinx.coroutines.flow.Flow

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): Flow<RecipeDTO>
    suspend fun searchForRecipes(page: Int, query: String): RecipePageDTO
}