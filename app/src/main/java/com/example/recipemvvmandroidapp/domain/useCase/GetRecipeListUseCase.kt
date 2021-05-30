package com.example.recipemvvmandroidapp.domain.useCase

import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.RecipeRepository
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.recipeRepository
import com.example.recipemvvmandroidapp.dependency.Dependency
import kotlinx.coroutines.flow.Flow

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {
    fun execute(
        query: String
    ): UseCaseResult<Flow<PagingData<RecipeDTO>>>
    {
        return try{
            UseCaseResult.Success(recipeRepository
                .searchForRecipes(query)
            )
        } catch(exception: Exception){
            UseCaseResult.Error(exception)
        }
    }
}

fun Dependency.UseCase.getRecipeListUseCase(): GetRecipeListUseCase
{
    return GetRecipeListUseCase(repository.recipeRepository())
}