package com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.domain.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO

private const val INITIAL_PAGE_INDEX = 1

/*
*
*  The Pager for this PagingSource is implemented in RecipeRepository class
*  in RecipeRepository.kt
*
* */
class RecipePagingSource(
    private val recipeService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper,
    private val query: String
): PagingSource<Int, RecipeDTO>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeDTO> {
        val currentPage = params.key ?: INITIAL_PAGE_INDEX
        return try{
            val response = recipeService.searchForRecipes(currentPage, query)
            val responseResults = recipeDTOMapper.mapListDomainModelToListDTO(response.results)

            //Can also return a LoadResult.Error here to catch a network call error etc.
            //However, UseCaseResult.Error is more preferable to catch an error, since the LoadResult.Error
            //will forward the error to UI layer
            LoadResult.Page(
                data = responseResults,
                prevKey = if(response.previous == null) null else currentPage - 1,
                nextKey = if(response.next == null) null else currentPage + 1
            )
        } catch (exception: Exception){
            Log.d("Exception in RecipePagingSource: load", "$exception")
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, RecipeDTO>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}