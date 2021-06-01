package com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.recipemvvmandroidapp.data.local.RecipeRoomDatabase
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.domain.repositoryInterface.RecipeRepositoryInterface
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class RecipeRepository(
    private val recipeService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper,
    private val recipeRoomDatabase: RecipeRoomDatabase
): RecipeRepositoryInterface {
    private val recipeDAO = recipeRoomDatabase.recipeDAO()

    private suspend fun getRecipeFromNetwork(id: Int): Recipe {
        recipeService.getRecipeById(id).let{
            recipeRoomDatabase.withTransaction {
                recipeDAO.insertRecipe(it)
            }
            return it
        }
    }

    override suspend fun getRecipeById(id: Int): RecipeDTO {
        return try{
            recipeDTOMapper.mapDomainModelToDTO(
                recipeDAO.findByRecipeId(id) ?: getRecipeFromNetwork(id)
            )
        } catch (exception: Exception){
            Log.d("Exception in RecipeRepository: getRecipeById", "$exception")
            throw exception
        }
    }

    override fun searchForRecipes(query: String): Flow<PagingData<RecipeDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = API_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RecipePagingSource(
                recipeService = recipeService,
                recipeDTOMapper = recipeDTOMapper,
                query = query
            )}
        ).flow
    }

    companion object{
        const val API_PAGE_SIZE = 30
    }
}
