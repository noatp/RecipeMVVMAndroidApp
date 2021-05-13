package com.example.recipemvvmandroidapp.viewModel

import com.example.recipemvvmandroidapp.dependency.Dependency
import androidx.lifecycle.ViewModel
import com.example.recipemvvmandroidapp.domain.model.Recipe

class RecipeDetailViewModel(

): ViewModel(){
    companion object{
        var instance: RecipeDetailViewModel? = null
    }
}

fun Dependency.ViewModel.recipeDetailViewModel(): RecipeDetailViewModel
{
    if(RecipeDetailViewModel.instance == null)
    {
        RecipeDetailViewModel.instance = RecipeDetailViewModel()
    }
    return RecipeDetailViewModel.instance!!
}

