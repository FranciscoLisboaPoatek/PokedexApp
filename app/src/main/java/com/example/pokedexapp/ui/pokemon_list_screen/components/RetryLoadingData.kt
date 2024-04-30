package com.example.pokedexapp.ui.pokemon_list_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.pokemon_list_screen.components.RetryLoadingDataTestTags.RETRY_BUTTON_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.components.RetryLoadingDataTestTags.RETRY_LOADING_DATA_TAG
import com.example.pokedexapp.ui.theme.TopBarBlueColor

object RetryLoadingDataTestTags {
    const val RETRY_BUTTON_TAG = "RETRY_BUTTON"
    const val RETRY_LOADING_DATA_TAG = "RETRY_LOADING_DATA"
}

@Composable
fun RetryLoadingData(
    reloadData: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.testTag(RETRY_LOADING_DATA_TAG),
    ) {
        IconButton(onClick = { reloadData() }, modifier = Modifier.testTag(RETRY_BUTTON_TAG)) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.retry_loading),
                tint = TopBarBlueColor,
                modifier = Modifier.size(50.dp),
            )
        }
        Text(
            text = stringResource(R.string.something_went_wrong),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 10.dp),
        )
    }
}

@Preview
@Composable
private fun RetryLoadingDataPreview() {
    Surface {
        RetryLoadingData(reloadData = { })
    }
}
