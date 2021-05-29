package com.example.recipemvvmandroidapp.ui.view.viewComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.ui.theme.*
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import kotlinx.coroutines.Dispatchers
import com.example.recipemvvmandroidapp.R
import com.google.accompanist.imageloading.LoadPainter

@Composable
fun CreateRecipeCard(
    recipeName: String,
    onClick: () -> Unit,
    painter: LoadPainter<Any>
){
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
    ){
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            when (painter.loadState) {
                is ImageLoadState.Success -> {
                    Image(
                        painter = painter,
                        contentDescription = "random image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(Shapes.medium),
                        contentScale = ContentScale.FillWidth
                    )
                }
//                // for preview
//                ImageLoadState.Empty -> {
//                    Image(
//                        painter = painterResource(id = R.drawable.blank),
//                        contentDescription = "loadplaceholder",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                            .clip(Shapes.medium)
//                    )
//                }
                else -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                    ){
                        CircularProgressIndicator()
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipeName,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun RecipeCard(
    recipeName: String,
    recipeImageUrl: String,
    onClick: () -> Unit,
)
{
    val painter = rememberCoilPainter(
        request = recipeImageUrl,
        requestBuilder = {
            dispatcher(Dispatchers.IO)
        },
        fadeIn = true
    )
    CreateRecipeCard(
        recipeName = recipeName,
        onClick = onClick,
        painter = painter
    )
}

@Preview
@Composable
fun PreviewCreateRecipeCard()
{
    val painter = rememberCoilPainter(
        request = "url",
        fadeIn = true,
        previewPlaceholder = R.drawable.load_placeholder
    )
    CreateRecipeCard(
        recipeName = "Test a long long long long name",
        onClick = {},
        painter = painter
    )
}