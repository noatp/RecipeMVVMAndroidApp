package com.noat.recipe_food2fork.data.repositoryImplementation.recipeRepository

import android.util.Log
import com.noat.recipe_food2fork.data.local.RecipeLocalDatabase
import com.noat.recipe_food2fork.data.remote.RecipeNetworkService
import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.domain.util.RecipeDTOMapper
import com.noat.recipe_food2fork.domain.util.recipeDTOMapper
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.dependency.recipeLocalDatabase
import com.noat.recipe_food2fork.dependency.recipeNetworkService
import com.noat.recipe_food2fork.domain.model.Recipe
import com.noat.recipe_food2fork.domain.model.RecipePageDTO
import com.noat.recipe_food2fork.domain.repositoryInterface.RecipeRepositoryInterface
import com.noat.recipe_food2fork.domain.util.RecipePageDTOMapper
import com.noat.recipe_food2fork.domain.util.recipePageDTOMapper
import java.io.IOException
import java.lang.Exception

class RecipeRepository(
    private val recipeNetworkService: RecipeNetworkService,
    private val recipeLocalDatabase: RecipeLocalDatabase,
    private val recipeDTOMapper: RecipeDTOMapper,
    private val recipePageDTOMapper: RecipePageDTOMapper
): RecipeRepositoryInterface {
    private suspend fun getRecipeByIdFromNetwork(id: Int): Recipe {
        try{
            val recipeFromNetwork = recipeNetworkService.getRecipeById(id)
            recipeLocalDatabase.insertRecipe(recipeFromNetwork)
            return recipeFromNetwork
        } catch (exception: Exception){
            Log.d("Rethrow exception in RecipeRepository: getRecipeByIdFromNetwork", "$exception")
            throw exception
        }
    }

    private suspend fun getRecipeByIdFromDB(id: Int): Recipe{
        try{
            return recipeLocalDatabase.getRecipeById(id)
        } catch (exception: Exception){
            Log.d("Rethrow exception in RecipeRepository: getRecipeByIdFromDB", "$exception")
            throw exception
        }
    }

    override suspend fun getRecipeById(id: Int): RecipeDTO {
        return try{
            recipeDTOMapper.mapDomainModelToDTO(getRecipeByIdFromNetwork(id))
        } catch (networkException: Exception){
            try{
                recipeDTOMapper.mapDomainModelToDTO(getRecipeByIdFromDB(id))
            } catch (dbException: Exception){
                Log.d("Rethrow exception in RecipeRepository: getRecipeById", "$dbException")
                throw dbException
            }
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
        service.recipeNetworkService(),
        service.recipeLocalDatabase(),
        service.recipeDTOMapper(),
        service.recipePageDTOMapper()
    )
}