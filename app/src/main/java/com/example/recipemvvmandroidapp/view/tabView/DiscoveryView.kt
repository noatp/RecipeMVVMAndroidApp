package com.example.recipemvvmandroidapp.view.tabView

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.ui.theme.DarkBackground

@Composable
fun DiscoveryView(

)
{
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(backgroundColor = DarkBackground){}
        },
        content = {
            Text("This is Discovery View")
        }
    )
}

@Composable
fun Dependency.View.DiscoveryView(
    router: RouterController
)
{
    DiscoveryView()
}

@Preview
@Composable
fun PreviewDiscoveryView()
{
    DiscoveryView()
}