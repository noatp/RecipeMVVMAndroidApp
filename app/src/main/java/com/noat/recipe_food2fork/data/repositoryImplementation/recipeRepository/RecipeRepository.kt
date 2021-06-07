package com.noat.recipe_food2fork.data.repositoryImplementation.recipeRepository

import android.util.Log
import com.noat.recipe_food2fork.data.remote.RecipeNetworkService
import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.domain.util.RecipeDTOMapper
import com.noat.recipe_food2fork.domain.util.recipeDTOMapper
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.dependency.recipeService
import com.noat.recipe_food2fork.domain.model.RecipePageDTO
import com.noat.recipe_food2fork.domain.repositoryInterface.RecipeRepositoryInterface
import com.noat.recipe_food2fork.domain.util.RecipePageDTOMapper
import com.noat.recipe_food2fork.domain.util.recipePageDTOMapper
import java.lang.Exception

class RecipeRepository(
    private val recipeNetworkService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper,
    private val recipePageDTOMapper: RecipePageDTOMapper
): RecipeRepositoryInterface {
    override suspend fun getRecipeById(id: Int): RecipeDTO {
        try{
            return recipeDTOMapper.mapDomainModelToDTO(recipeNetworkService.getRecipeById(id))
        } catch (exception: Exception){
            Log.d("Rethrow exception in RecipeRepository: getRecipeById", "$exception")
            throw exception
        }
    }

    override suspend fun searchForRecipes(page: Int, query: String): RecipePageDTO {
        try{
            return(recipePageDTOMapper.mapResponseToRecipeDTOMapper(recipeNetworkService.searchForRecipes(page, query)))
        } catch (exception: Exception){
            Log.d("Rethrow exception in RecipeRepository: searchForRecipes", "$exception")
            throw exception
        }
    }
}

fun Dependency.Repository.recipeRepository(): RecipeRepository
{
    return RecipeRepository(
        service.recipeService(),
        service.recipeDTOMapper(),
        service.recipePageDTOMapper()
    )
}