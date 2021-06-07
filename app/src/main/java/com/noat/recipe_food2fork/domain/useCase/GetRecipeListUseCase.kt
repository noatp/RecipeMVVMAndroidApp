package com.noat.recipe_food2fork.domain.useCase

import com.noat.recipe_food2fork.data.repositoryImplementation.recipeRepository.RecipeRepository
import com.noat.recipe_food2fork.data.repositoryImplementation.recipeRepository.recipeRepository
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.domain.model.RecipePageDTO

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        page: Int,
        query: String
    ): UseCaseResult<RecipePageDTO>
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