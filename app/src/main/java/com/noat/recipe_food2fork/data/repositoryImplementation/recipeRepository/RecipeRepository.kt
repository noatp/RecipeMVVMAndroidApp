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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RecipeRepository(
    private val recipeNetworkService: RecipeNetworkService,
    private val recipeLocalDatabase: RecipeLocalDatabase,
    private val recipeDTOMapper: RecipeDTOMapper,
    private val recipePageDTOMapper: RecipePageDTOMapper
): RecipeRepositoryInterface {

    private val currentQuery: String = ""

    override suspend fun getRecipeById(id: Int): Flow<RecipeDTO> {
        val recipeFromDB: Flow<Recipe> = recipeLocalDatabase.getRecipeById(id)
        return recipeFromDB.map { recipe: Recipe ->
            if(recipe == Recipe.empty){
                //NOTE: flow is executed within a coroutine -> when calling a suspend function,
                // the flow is suspended until the suspend function returns.
                //SEQUENTIALLY execute the network call, and then store it into database
                val recipeFromNetwork = recipeNetworkService.getRecipeById(id)
                recipeLocalDatabase.insertRecipe(recipeFromNetwork)
            }
            recipeDTOMapper.mapDomainModelToDTO(recipe)
        }.catch{ exception: Throwable ->
            Log.d("Rethrowing in RecipeRepository: getRecipeById", "$exception")
            throw exception
        }
    }

    //this suspend function will go through all the result pages of a query,
    //copy all the data into local database
    override suspend fun searchForRecipes(page: Int, query: String): RecipePageDTO {
        return(recipePageDTOMapper.mapResponseToRecipeDTOMapper(recipeNetworkService.searchForRecipes(page, query)))
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