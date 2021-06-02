package com.example.recipemvvmandroidapp.domain.useCase

import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.RecipeRepository

class GetRecipeDetailUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        id: Int
    ): UseCaseResult<RecipeDTO>
    {
        return try{
            UseCaseResult.Success(recipeRepository.getRecipeById(id))
        } catch(exception: Exception){
            UseCaseResult.Error(exception)
        }
    }
}
