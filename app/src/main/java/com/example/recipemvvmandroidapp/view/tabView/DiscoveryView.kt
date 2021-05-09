package com.example.recipemvvmandroidapp.view.tabView

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.ui.theme.DarkBackground

@Composable
fun Dependency.View.DiscoveryView()
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