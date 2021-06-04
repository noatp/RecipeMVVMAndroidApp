package com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository

import android.util.Log
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.domain.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.domain.util.recipeDTOMapper
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.dependency.recipeService
import com.example.recipemvvmandroidapp.domain.repositoryInterface.RecipeRepositoryInterface
import java.lang.Exception

class RecipeRepository(
    private val recipeNetworkService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper
): RecipeRepositoryInterface {
    override suspend fun getRecipeById(id: Int): RecipeDTO {
        try{
            return recipeDTOMapper.mapDomainModelToDTO(
                recipeNetworkService.getRecipeById(id)
            )
        } catch (exception: Exception){
            Log.d("Rethrow exception in RecipeRepository: getRecipeById", "$exception")
            throw exception
        }
    }

    override suspend fun searchForRecipes(page: Int, query: String): List<RecipeDTO> {
        try{
            return recipeDTOMapper.mapListDomainModelToListDTO(
                recipeNetworkService.searchForRecipes(page, query)
            )
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
        service.recipeDTOMapper()
    )
}