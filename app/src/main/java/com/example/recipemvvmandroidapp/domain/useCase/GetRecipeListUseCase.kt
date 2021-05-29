package com.example.recipemvvmandroidapp.domain.useCase

import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.RecipeRepository
import com.example.recipemvvmandroidapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {
    fun execute(
        query: String
    ): UseCaseResult<Flow<PagingData<Recipe>>>
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