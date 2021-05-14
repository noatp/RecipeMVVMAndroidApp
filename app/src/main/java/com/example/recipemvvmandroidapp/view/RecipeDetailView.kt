package com.example.recipemvvmandroidapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipemvvmandroidapp.dependency.Dependency

@Composable
fun Dependency.View.RecipeDetailView(
    recipeId: Int
)
{
    Surface() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //Image

            //Title
            Text("Id: $recipeId")
            Text("Publisher")
            Text("Rating")

        }
    }
}