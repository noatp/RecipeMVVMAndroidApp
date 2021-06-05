package com.example.recipemvvmandroidapp.domain.util

import com.example.recipemvvmandroidapp.data.remote.model.Response
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.model.RecipePageDTO

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