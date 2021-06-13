package com.noat.recipe_food2fork.domain.util

import com.noat.recipe_food2fork.data.SearchResponse
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.domain.model.RecipePageDTO

class RecipePageDTOMapper(
    private val recipeDTOMapper: RecipeDTOMapper
){
    fun mapResponseToRecipeDTOMapper(SearchResponse: SearchResponse): RecipePageDTO {
        return RecipePageDTO(
            recipeList = recipeDTOMapper.mapListDomainModelToListDTO(SearchResponse.results),
            hasNextPage = SearchResponse.next != null,
            hasPreviousPage = SearchResponse.previous != null
        )
    }
}

fun Dependency.Service.recipePageDTOMapper(): RecipePageDTOMapper{
    return RecipePageDTOMapper(recipeDTOMapper = recipeDTOMapper())
}