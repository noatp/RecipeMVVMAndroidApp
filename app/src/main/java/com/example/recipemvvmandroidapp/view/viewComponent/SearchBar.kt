package com.example.recipemvvmandroidapp.view.viewComponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.ui.theme.*

@Composable
fun SearchBar(
    textContent: String,
    onValueChange: (String) -> Unit,
    labelContent: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    focusedIndicatorColor: Color = DarkBackground,
    cursorColor: Color = DarkBackground,
    focusedLabelColor: Color = LightBackground,
    unfocusedLabelColor: Color = DarkBackground,
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
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text(text = labelContent) },
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
            focusedIndicatorColor = focusedIndicatorColor,
            cursorColor = cursorColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedLabelColor = unfocusedLabelColor,
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