package com.example.recipemvvmandroidapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.R
import com.example.recipemvvmandroidapp.ui.theme.Shapes
import com.example.recipemvvmandroidapp.ui.viewModel.RecipeDetailViewModel
import com.example.recipemvvmandroidapp.ui.viewModel.RecipeForDetailView
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainter
import kotlinx.coroutines.Dispatchers

@Composable
fun CreateRecipeDetailView(
    loadError: Boolean,
    painter: LoadPainter<Any>,
    recipe:  RecipeForDetailView
){
    Surface() {
        if(loadError){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = "An error occurred",
                    style = MaterialTheme.typography.h5
                )
            }
        }
        else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                //Image
                item{
                    when (painter.loadState) {
                        is ImageLoadState.Success -> {
                            Image(
                                painter = painter,
                                contentDescription = recipe.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .clip(Shapes.medium),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                        else -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(250.dp)
                                    .fillMaxWidth(),
                            ){
                                CircularProgressIndicator()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    //Title
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    //Ingredients
                    recipe.ingredients.map {
                        Text(
                            text = "- $it",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeDetailView(
    recipeDetailViewModel: RecipeDetailViewModel
)
{
    val uiState = recipeDetailViewModel.uiState.collectAsState().value
    val loadError = uiState.loadError
    val recipe = uiState.recipeForDetailView
    val painter = rememberCoilPainter(
        request = recipe.featuredImage,
        requestBuilder = {
            dispatcher(Dispatchers.IO)
        },
        fadeIn = true
    )
    CreateRecipeDetailView(
        loadError = loadError,
        painter = painter,
        recipe = recipe
    )
}

@Preview
@Composable
fun PreviewRecipeDetailView()
{
    val painter = rememberCoilPainter(
        request = "url",
        previewPlaceholder = R.drawable.load_placeholder
    )

    CreateRecipeDetailView(
        loadError = false,
        painter = painter,
        recipe = RecipeForDetailView(
            title = "This is a title",
            featuredImage = "url",
            ingredients = listOf("ingredient1", "ingredient2", "ingredient3")
        )
    )
}
