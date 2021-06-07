package com.noat.recipe_food2fork.domain.repositoryInterface

import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.domain.model.RecipePageDTO

interface RecipeRepositoryInterface {
    suspend fun getRecipeById(id: Int): RecipeDTO
    suspend fun searchForRecipes(page: Int, query: String): RecipePageDTO
}