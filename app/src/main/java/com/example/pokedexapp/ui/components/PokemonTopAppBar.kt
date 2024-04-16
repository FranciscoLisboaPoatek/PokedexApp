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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun PokemonTopAppBar(
    searchText: String,
    searchMode: Boolean,
    areActionsEnabled: Boolean,
    onSearchTextChange: (String) -> Unit,
    onSendNotificationClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    var wasSearchClicked by remember { mutableStateOf(false) }

    if (searchMode) {
        SearchPokemonTopAppBar(
            searchText = searchText,
            wasSearchClicked = wasSearchClicked,
            onSearchTextChange = {
                wasSearchClicked = false
                onSearchTextChange(it)
            },
            onCloseSearchCLick = { onSearchClick() },
        )
    } else {
        LogoPokemonTopAppBar(
            areActionsEnabled = areActionsEnabled,
            onSendNotificationClick = onSendNotificationClick,
            onSearchClick = {
                wasSearchClicked = true
                onSearchClick()
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogoPokemonTopAppBar(
    areActionsEnabled: Boolean,
    onSendNotificationClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            PokemonLogo(modifier = Modifier.height(60.dp))
        },
        actions = {
            TopAppBarDailyNotificationButton(
                enabled = areActionsEnabled,
                onClick = {
                    onSendNotificationClick()
                },
            )
            TopAppBarSearchButton(
                enabled = areActionsEnabled,
                onClick = {
                    onSearchClick()
                },
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBlueColor,
            ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchPokemonTopAppBar(
    searchText: String,
    wasSearchClicked: Boolean,
    onSearchTextChange: (String) -> Unit,
    onCloseSearchCLick: () -> Unit,
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
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SearchTextField(
                        text = searchText,
                        onSearchTextChange = {
                            onSearchTextChange(it)
                        },
                        focusRequester = focusRequester,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                    )
                }
            },
        actions = {
            TopAppBarCloseSearchButton(
                onClick = {
                    onSearchTextChange("")
                    onCloseSearchCLick()
                },
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBlueColor,
            ),
    )
}

@Composable
private fun SearchTextField(
    text: String,
    onSearchTextChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = text,
        onValueChange = {
            onSearchTextChange(it)
        },
        textStyle =
            MaterialTheme.typography.titleLarge.copy(
                color = Color.Black,
            ),
        cursorBrush = SolidColor(Color.Black),
        singleLine = true,
        modifier =
            modifier
                .focusRequester(focusRequester),
    ) { innerTextField ->
        if (text.isBlank()) {
            Text(
                text = stringResource(R.string.pokemon_search_hint),
                color = Color.Black.copy(alpha = 0.5f),
                maxLines = 1,
            )
        }
        innerTextField()
    }
}

@Composable
private fun PokemonLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.pokemon_logo),
        contentDescription = null,
        alignment = Alignment.Center,
        modifier = modifier,
    )
}

@Composable
private fun TopAppBarIconButton(
    icon: ImageVector,
    enabled: Boolean,
    onClick: () -> Unit,
    contentDescription: String? = null,
) {
    IconButton(onClick = onClick, enabled = enabled) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (enabled) Color.White else Color.LightGray,
        )
    }
}

@Composable
private fun TopAppBarDailyNotificationButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    TopAppBarIconButton(
        icon = ImageVector.vectorResource(id = R.drawable.baseline_notifications_active_24),
        enabled = enabled,
        onClick = onClick,
        contentDescription = stringResource(R.string.show_random_pokemon_notification),
    )
}

@Composable
private fun TopAppBarSearchButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    TopAppBarIconButton(
        icon = Icons.Default.Search,
        enabled = enabled,
        onClick = onClick,
        contentDescription = stringResource(R.string.open_search),
    )
}

@Composable
private fun TopAppBarCloseSearchButton(onClick: () -> Unit) {
    TopAppBarIconButton(
        icon = Icons.Default.Close,
        enabled = true,
        onClick = onClick,
        contentDescription = stringResource(id = R.string.close_search),
    )
}

@Preview
@Composable
fun PokemonTopAppBarPreview() {
    PokemonTopAppBar(
        searchText = "",
        searchMode = false,
        areActionsEnabled = true,
        onSearchTextChange = { },
        onSendNotificationClick = { },
        onSearchClick = { },
    )
}

@Preview
@Composable
fun PokemonTopAppBarSearchModePreview() {
    PokemonTopAppBar(
        searchText = "",
        searchMode = true,
        areActionsEnabled = true,
        onSearchTextChange = { },
        onSendNotificationClick = { },
        onSearchClick = { },
    )
}
