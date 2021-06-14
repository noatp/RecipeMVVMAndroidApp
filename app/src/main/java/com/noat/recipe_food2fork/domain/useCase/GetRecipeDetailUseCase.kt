package com.noat.recipe_food2fork.domain.useCase

import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.data.repositoryImplementation.recipeRepository.RecipeRepository
import com.noat.recipe_food2fork.data.repositoryImplementation.recipeRepository.recipeRepository
import com.noat.recipe_food2fork.dependency.Dependency
import kotlinx.coroutines.flow.Flow

class GetRecipeDetailUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        id: Int
    ): UseCaseResult<Flow<RecipeDTO>>
    {
        return try{
            UseCaseResult.Success(recipeRepository.getRecipeById(id))
        } catch(exception: Exception){
            UseCaseResult.Error(exception)
        }
    }
}

fun Dependency.UseCase.getRecipeDetailUseCase(): GetRecipeDetailUseCase
{
    return GetRecipeDetailUseCase(repository.recipeRepository())
}