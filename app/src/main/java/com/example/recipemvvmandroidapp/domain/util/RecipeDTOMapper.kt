package com.example.recipemvvmandroidapp.domain.util

import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.model.Recipe

class RecipeDTOMapper(){
    fun mapDomainModelToDTO(domainModel: Recipe): RecipeDTO {
        return RecipeDTO(
            id = domainModel.id,
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            ingredients = domainModel.ingredients,
        )
    }

    fun mapListDomainModelToListDTO(listDomainModel: List<Recipe>): List<RecipeDTO> {
        return listDomainModel.map { domainModel: Recipe ->
            mapDomainModelToDTO(domainModel = domainModel)
        }
    }
}

fun Dependency.Service.recipeDTOMapper(): RecipeDTOMapper{
    return RecipeDTOMapper()
}