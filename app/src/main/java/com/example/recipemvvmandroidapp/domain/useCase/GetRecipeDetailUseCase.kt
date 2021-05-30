package com.example.recipemvvmandroidapp.domain.useCase

import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.RecipeRepository
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.recipeRepository
import com.example.recipemvvmandroidapp.dependency.Dependency

class GetRecipeDetailUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        id: Int
    ): UseCaseResult<RecipeDTO>
    {
        return try{
            UseCaseResult.Success(recipeRepository
                .getRecipeById(id)
            )
        } catch(exception: Exception){
            UseCaseResult.Error(exception)
        }
    }
}

fun Dependency.UseCase.getRecipeDetailUseCase(): GetRecipeDetailUseCase
{
    return GetRecipeDetailUseCase(repository.recipeRepository())
}