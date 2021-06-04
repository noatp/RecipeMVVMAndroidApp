package com.example.recipemvvmandroidapp.domain.useCase

import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.RecipeRepository
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.recipeRepository
import com.example.recipemvvmandroidapp.dependency.Dependency

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        page: Int,
        query: String
    ): UseCaseResult<List<RecipeDTO>>
    {
        return try{
            UseCaseResult.Success(recipeRepository.searchForRecipes(page, query))
        } catch(exception: Exception){
            UseCaseResult.Error(exception)
        }
    }
}

fun Dependency.UseCase.getRecipeListUseCase(): GetRecipeListUseCase
{
    return GetRecipeListUseCase(repository.recipeRepository())
}