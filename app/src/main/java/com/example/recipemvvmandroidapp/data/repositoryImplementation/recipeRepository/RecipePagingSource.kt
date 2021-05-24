package com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.data.remote.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository.RecipeRepository.Companion.API_PAGE_SIZE
import com.example.recipemvvmandroidapp.domain.model.Recipe
import java.lang.Exception

private const val INITIAL_PAGE_INDEX = 1

class RecipePagingSource(
    private val recipeService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper,
    private val query: String
): PagingSource<Int, Recipe>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val currentPage = params.key ?: INITIAL_PAGE_INDEX
        return try{
            val response = recipeDTOMapper
                .mapToListDomainModel(
                    recipeService.searchForRecipes(currentPage, query)
                )
            val nextKey = if(response.isEmpty()){
                null
            }
            else{
                currentPage + (params.loadSize / API_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if(currentPage == INITIAL_PAGE_INDEX) null else currentPage - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition = anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition = anchorPosition)?.nextKey?.minus(1)
        }
    }
}