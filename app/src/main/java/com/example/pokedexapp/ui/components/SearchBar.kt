package com.example.pokedexapp.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun SearchBar(
    searchText: String,
    modifier: Modifier = Modifier,
    onClearText: () -> Unit,
    onSearchTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = {
            if (it.isEmpty()) onClearText() else onSearchTextChange(it)
        },
        modifier = modifier,
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TopBarBlueColor
        ),
        trailingIcon = {
            IconButton({
                onClearText()
            }) {
                Icon(
                    Icons.Default.Clear,
                    null
                )
            }
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                null
            )
        },
    )
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar("pikachu", onClearText = {}) {}
}

@Preview
@Composable
private fun EmptySearchBarPreview() {
    SearchBar("", onClearText = {}) {}
}