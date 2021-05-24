package com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.data.remote.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.repositoryInterface.RecipeRepositoryInterface
import kotlinx.coroutines.flow.Flow

class RecipeRepository(
    private val recipeService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper
): RecipeRepositoryInterface {
    override suspend fun getRecipeById(id: Int): Recipe {
        return recipeDTOMapper
            .mapToDomainModel(recipeService
                .getRecipeById(id)
            )
    }

    override fun searchForRecipes(query: String): Flow<PagingData<Recipe>> {
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
