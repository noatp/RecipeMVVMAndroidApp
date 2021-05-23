package com.example.recipemvvmandroidapp.domain.useCase

import com.example.recipemvvmandroidapp.data.repositoryImplementation.RecipeRepository
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.model.Recipe

class GetRecipeDetailUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        id: Int
    ): UseCaseResult<Recipe>
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