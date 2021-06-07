package com.noat.recipe_food2fork.domain.util

import com.noat.recipe_food2fork.data.remote.model.Response
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.domain.model.RecipePageDTO

class RecipePageDTOMapper(
    private val recipeDTOMapper: RecipeDTOMapper
){
    fun mapResponseToRecipeDTOMapper(response: Response): RecipePageDTO {
        return RecipePageDTO(
            recipeList = recipeDTOMapper.mapListDomainModelToListDTO(response.results),
            hasNextPage = response.next != null,
            hasPreviousPage = response.previous != null
        )
    }
}

fun Dependency.Service.recipePageDTOMapper(): RecipePageDTOMapper{
    return RecipePageDTOMapper(recipeDTOMapper = recipeDTOMapper())
}