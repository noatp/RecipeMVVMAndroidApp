package com.noat.recipe_food2fork.domain.util

import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.domain.model.Recipe

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