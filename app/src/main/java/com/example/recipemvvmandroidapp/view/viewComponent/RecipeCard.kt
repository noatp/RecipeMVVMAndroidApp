package com.example.recipemvvmandroidapp.view.viewComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.ui.theme.*
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import kotlinx.coroutines.Dispatchers
import com.example.recipemvvmandroidapp.R

@Composable
fun RecipeCard(
    recipeName: String,
    recipeImageUrl: String,
    onClick: () -> Unit
)
{
    val painter = rememberCoilPainter(
        request = recipeImageUrl,
        requestBuilder = {
            dispatcher(Dispatchers.IO)
            placeholder(R.drawable.load_placeholder)
        },
        fadeIn = true,
        previewPlaceholder = R.drawable.load_placeholder
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        backgroundColor = DarkBackground,
        shape = Shapes.small,
        border = BorderStroke(1.dp, DarkBackgroundVariant),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                when (painter.loadState) {
                    ImageLoadState.Loading -> {
                        // Display a circular progress indicator whilst loading
                        CircularProgressIndicator(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    is ImageLoadState.Error -> {
                        // If you wish to display some content if the request fails
                    }
                }
                Image(
                    painter = painter,
                    contentDescription = "random image",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(Shapes.medium),
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    text = recipeName,
                    modifier = Modifier
                        .padding(8.dp),
                    color = LightText,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@Preview
@Composable
fun PreviewRecipeCard()
{
    RecipeCard(
        recipeName = "Test a long long long long name",
        recipeImageUrl = "https://nyc3.digitaloceanspaces.com/food2fork/food2fork-static/featured_images/583/featured_image.png",
        onClick = {}
    )
}