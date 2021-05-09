package com.example.recipemvvmandroidapp.view.viewComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.ui.theme.*


val iconMap = mapOf<String, ImageVector>(
    "Search" to Icons.Outlined.Search,
    "Discovery" to Icons.Outlined.Lightbulb
)

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    titles: List<String>,
    tabSelected: TabViewDestination,
    onTabSelected: (TabViewDestination) -> Unit
){
    TabRow(
        selectedTabIndex = tabSelected.ordinal,
        modifier = modifier.fillMaxSize(),
        indicator = @Composable { tabPositions ->
            Box(
                modifier
                    .tabIndicatorOffset(tabPositions[tabSelected.ordinal])
                    .fillMaxSize()
                    .background(color = DarkBackground.copy(alpha = 0.2f)),
                content = {}
            )
        },
    ) {
        titles.forEachIndexed{ index, title ->
            NavTab(
                selected = index == tabSelected.ordinal,
                onTabSelected = onTabSelected,
                index = index,
                title = title)
        }
    }
}

@Composable
fun NavTab(
    selected: Boolean,
    onTabSelected: (TabViewDestination) -> Unit,
    index: Int,
    title: String
)
{
    Tab(
        selected = selected,
        onClick = {onTabSelected(TabViewDestination.values()[index])},
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        selectedContentColor = DarkBackground,
        unselectedContentColor = DarkText,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                iconMap[title]?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "menu icon to open drawer")
                }
                Text(
                    text = title,
                    maxLines = 1
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewBottomNavBar()
{
    BottomNavBar(
        titles = TabViewDestination.values().map{it.name},
        tabSelected =  TabViewDestination.Search,
        onTabSelected = {}
    )
}