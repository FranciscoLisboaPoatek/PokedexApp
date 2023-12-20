package com.example.pokedexapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenOnEvent
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun PokemonTopAppBar(
    searchMode: Boolean,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
) {

    if (searchMode) SearchPokemonTopAppBar(
        onSearchTextChange = {
            onEvent(
                PokemonListScreenOnEvent.OnSearchTextValueChange(it)
            )
        },
        onCloseSearchCLick = { onEvent(PokemonListScreenOnEvent.OnSearchClick) })
    else LogoPokemonTopAppBar(onSearchClick = { onEvent(PokemonListScreenOnEvent.OnSearchClick) })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoPokemonTopAppBar(
    onSearchClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            PokemonLogo(modifier = Modifier.height(60.dp))
        },
        actions = {
            TopAppBarIconButton(
                icon = Icons.Default.Search,
                onClick = {
                    onSearchClick()
                },
                contentDescription = stringResource(R.string.open_search)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBarBlueColor
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPokemonTopAppBar(
    onSearchTextChange: (String) -> Unit,
    onCloseSearchCLick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        title =
        {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchTextField(
                    text = searchText,
                    onSearchTextChange = {
                        searchText = it
                        onSearchTextChange(it)
                    },
                    focusRequester = focusRequester,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                )
            }
        },
        actions = {
            TopAppBarIconButton(
                icon = Icons.Default.Close,
                onClick = {
                    searchText = ""
                    onSearchTextChange(searchText)
                    onCloseSearchCLick()
                },
                contentDescription = stringResource(R.string.close_search)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBarBlueColor
        ),
    )
}

@Composable
fun SearchTextField(
    text: String,
    onSearchTextChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = text,
        onValueChange = {
            onSearchTextChange(it)
        },
        textStyle = MaterialTheme.typography.titleLarge.copy(
            color = Color.Black
        ),
        cursorBrush = SolidColor(Color.Black),
        singleLine = true,
        modifier = modifier
            .focusRequester(focusRequester)
    ) { innerTextField ->
        if (text.isBlank()) {
            Text(
                text = stringResource(R.string.pokemon_search_hint),
                color = Color.Black.copy(alpha = 0.5f),
                maxLines = 1
            )
        }
        innerTextField()
    }
}

@Composable
fun PokemonLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.pokemon_logo),
        contentDescription = null,
        alignment = Alignment.Center,
        modifier = modifier
    )
}

@Composable
fun TopAppBarIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun PokemonTopAppBarPreview() {
    PokemonTopAppBar(false) {

    }
}

@Preview
@Composable
fun PokemonTopAppBarSearchModePreview() {
    PokemonTopAppBar(true) {

    }
}