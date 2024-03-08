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
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun PokemonTopAppBar(
    searchText: String,
    searchMode: Boolean,
    onSearchTextChange: (String) -> Unit,
    handleSearchClick: () -> Unit
) {
    var wasSearchClicked by remember { mutableStateOf(false) }

    if (searchMode) SearchPokemonTopAppBar(
        searchText = searchText,
        wasSearchClicked = wasSearchClicked,
        onSearchTextChange = { wasSearchClicked = false; onSearchTextChange(it) },
        onCloseSearchCLick = { handleSearchClick() })
    else LogoPokemonTopAppBar(onSearchClick = { wasSearchClicked = true; handleSearchClick()})

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogoPokemonTopAppBar(
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
private fun SearchPokemonTopAppBar(
    searchText: String,
    wasSearchClicked: Boolean,
    onSearchTextChange: (String) -> Unit,
    onCloseSearchCLick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    if (wasSearchClicked) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
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
                    onSearchTextChange("")
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
private fun SearchTextField(
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
private fun PokemonLogo(
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
private fun TopAppBarIconButton(
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
    PokemonTopAppBar("",false,{}) {

    }
}

@Preview
@Composable
fun PokemonTopAppBarSearchModePreview() {
    PokemonTopAppBar("",true,{}) {

    }
}