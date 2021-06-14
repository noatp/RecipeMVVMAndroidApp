package com.noat.recipe_food2fork.domain.model

data class RecipeDTO(
    val id: Int,
    val title: String,
    val featuredImage: String,
    val ingredients: List<String> = listOf(),
){
    companion object{
        val empty = RecipeDTO(
            id = 0,
            title = "",
            featuredImage = "",
            ingredients = listOf()
        )
    }
}