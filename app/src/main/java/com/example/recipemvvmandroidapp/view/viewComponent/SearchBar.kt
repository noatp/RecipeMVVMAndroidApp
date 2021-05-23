package com.example.recipemvvmandroidapp.view.viewComponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchBar(
    textContent: String,
    onValueChange: (String) -> Unit,
    labelContent: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    autoCorrect: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onSearch: () -> Unit
)
{
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = textContent,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.body1,
        label = { Text(text = labelContent, style = MaterialTheme.typography.subtitle1) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            autoCorrect = autoCorrect,
            keyboardType = keyboardType,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                focusManager.clearFocus(forcedClear = true) // close keyboard
            }),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        singleLine = singleLine,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colors.onSurface,
            cursorColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.onBackground
        )
    )
}

@Preview
@Composable
fun PreviewSearchBar()
{
    SearchBar(
        textContent = "",
        onValueChange = { /*TODO*/ },
        labelContent = "Search for recipe",
        onSearch = {}
    )
}