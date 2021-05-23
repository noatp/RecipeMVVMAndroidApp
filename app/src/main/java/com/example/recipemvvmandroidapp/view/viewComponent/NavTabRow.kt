package com.example.recipemvvmandroidapp.view.viewComponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.ui.theme.*

private val TabHeight = 60.dp
private const val InactiveTabOpacity = 0.50f

private const val TabFadeAnimationDuration = 150
private const val TabFadeAnimationDelay = 100

@Composable
fun NavTabRow(
    tabs: List<TabViewDestination>,
    currentTab: TabViewDestination,
    onTabSelected: (TabViewDestination) -> Unit
){
    Surface(
        modifier = Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.selectableGroup()
        ){
            tabs.forEach{tab: TabViewDestination ->
                NavTab(
                    text = tab.name,
                    icon = tab.icon,
                    onSelected = { onTabSelected(tab) },
                    selected = currentTab == tab
                )
            }
        }
    }
}

@Composable
private fun NavTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
){
    val color = MaterialTheme.colors.onSurface
    val animSpec = remember {
        tween<Color>(
            durationMillis = TabFadeAnimationDuration,
            easing = LinearEasing,
            delayMillis = TabFadeAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
        if (selected) {
            Spacer(Modifier.width(12.dp))
            Text(text, color = tabTintColor)
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavBar()
{
    Scaffold (
        bottomBar = {
            BottomAppBar() {
                NavTabRow(
                    tabs = TabViewDestination.values().toList(),
                    currentTab = TabViewDestination.Search,
                    onTabSelected = {}
                )
            }
        }
    ){

    }
}