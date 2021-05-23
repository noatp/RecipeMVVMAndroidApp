package com.example.recipemvvmandroidapp.data.remote.util

import com.example.recipemvvmandroidapp.data.remote.model.RecipeDTO
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

class RecipeDTOMapper(): DomainMapper<RecipeDTO, Recipe> {
    override fun mapToDomainModel(objectModel: RecipeDTO): Recipe {
        return Recipe(
            id = objectModel.pk,
            title = objectModel.title,
            featuredImage = objectModel.featuredImage,
            rating = objectModel.rating,
            publisher = objectModel.publisher,
            sourceUrl = objectModel.sourceUrl,
            ingredients = objectModel.ingredients,
            dateAdded = objectModel.dateAdded,
            dateUpdated = objectModel.dateUpdated,
        )
    }

    override fun mapToListDomainModel(listObjectModel: List<RecipeDTO>): List<Recipe> {
        return listObjectModel.map { mapToDomainModel(it) }
    }
}
