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

    private val currentQuery: String = ""

    private suspend fun storeRecipeFromNetwork(id: Int) {
        val recipeFromNetwork = recipeNetworkService.getRecipeById(id)
        recipeLocalDatabase.insertRecipe(recipeFromNetwork)
    }

    private suspend fun getRecipeByIdFromDB(id: Int): Recipe{
        return recipeLocalDatabase.getRecipeById(id)
    }

    override suspend fun getRecipeById(id: Int): RecipeDTO {
        storeRecipeFromNetwork(id)
        return recipeDTOMapper.mapDomainModelToDTO(getRecipeByIdFromDB(id))
    }

    //this suspend function will go through all the result pages of a query,
    //copy all the data into local database
    private suspend fun searchForRecipesFromNetwork(query: String){
        var currentPage = 1
        do{
            var currentResponse = recipeNetworkService.searchForRecipes(currentPage, query)
            currentResponse.results.map{ recipe: Recipe ->
                recipeLocalDatabase.insertRecipe(recipe = recipe)
            }
            currentPage++
        } while (currentResponse.next != null)
    }

    override suspend fun searchForRecipes(page: Int, query: String): RecipePageDTO {
        if(query != currentQuery){
            searchForRecipesFromNetwork(query) // insert recipe list from network into local db
        }
        return(recipePageDTOMapper.mapResponseToRecipeDTOMapper(recipeLocalDatabase.searchForRecipes(page, query)))
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