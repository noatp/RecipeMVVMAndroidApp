package com.example.recipemvvmandroidapp.domain.useCase

import com.example.recipemvvmandroidapp.data.repositoryImplementation.RecipeRepository
import com.example.recipemvvmandroidapp.domain.model.Recipe

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        page: Int,
        query: String
    ): UseCaseResult<List<Recipe>>
    {
        return try{
            UseCaseResult.Success(recipeRepository
                .searchForRecipes(page, query)
            )
        } catch(exception: Exception){
            UseCaseResult.Error(exception)
        }
    }
}