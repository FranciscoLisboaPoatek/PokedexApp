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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun RetryLoadingData(
    reloadData: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        IconButton(onClick = { reloadData() }) {
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
