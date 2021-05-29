package com.example.recipemvvmandroidapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.R
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.ui.theme.Shapes
import com.example.recipemvvmandroidapp.ui.viewModel.RecipeDetailViewModel
import com.example.recipemvvmandroidapp.ui.viewModel.recipeDetailViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainter
import kotlinx.coroutines.Dispatchers

@Composable
fun RecipeDetailView(
    painter: LoadPainter<Any>,
    recipe:  RecipeDetailViewModel.RecipeForDetailView
){
    Surface() {
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
                            contentDescription = "${recipe.title}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(Shapes.medium),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    // for preview
                    ImageLoadState.Empty -> {
                        Image(
                            painter = painterResource(id = R.drawable.blank),
                            contentDescription = "loadplaceholder",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(Shapes.medium)
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

@Composable
fun Dependency.View.RecipeDetailView(recipeId: Int)
{
    val recipeDetailViewModel = viewModel.recipeDetailViewModel()
    val recipe = recipeDetailViewModel.recipeForDetailView.value
    val painter = rememberCoilPainter(
        request = recipe.featuredImage,
        requestBuilder = {
            dispatcher(Dispatchers.IO)
        },
        fadeIn = true
    )

    recipeDetailViewModel.onLaunch(recipeId)

    RecipeDetailView(
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

    RecipeDetailView(
        painter = painter,
        recipe = RecipeDetailViewModel.RecipeForDetailView(
            title = "This is a title",
            featuredImage = "url",
            ingredients = listOf("ingredient1", "ingredient2", "ingredient3")
        )
    )
}
