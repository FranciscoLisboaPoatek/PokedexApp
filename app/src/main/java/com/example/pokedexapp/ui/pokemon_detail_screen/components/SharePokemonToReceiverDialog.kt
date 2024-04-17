package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pokedexapp.R
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SharePokemonToReceiverDialog(
    isError: Boolean,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onTokenChange: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
        ) {
            Column {
                CloseDialogIconButton(close = onDismiss, modifier = Modifier.align(Alignment.End))

                Spacer(modifier = Modifier.height(5.dp))

                DialogContent(isError = isError, text = text, onTokenChange = onTokenChange, onConfirm = onConfirm)
            }
        }
    }
}

@Composable
private fun CloseDialogIconButton(
    close: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = close, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.close_dialog),
        )
    }
}

@Composable
fun DialogContent(
    isError: Boolean,
    text: String,
    onTokenChange: (String) -> Unit,
    onConfirm: () -> Unit,
) {
    Column(
        modifier =
            Modifier.padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 30.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTokenChange(it) },
            placeholder = { Text(text = stringResource(R.string.share_pokemon_to_receiver_dialog_placeholder)) },
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DialogButtons(
            onConfirm = onConfirm,
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isError) {
            ErrorSharingPokemon()
        }
    }
}

@Composable
private fun DialogButtons(onConfirm: () -> Unit) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboardManager.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedButton(onClick = {
            scope.launch {
                val localToken = Firebase.messaging.token.await()
                clipboard.setText(AnnotatedString(localToken))
            }
        }) {
            Text(text = stringResource(R.string.copy_my_token_button))
        }

        OutlinedButton(onClick = { onConfirm() }) {
            Text(text = stringResource(R.string.send_button))
        }
    }
}

@Composable
private fun ErrorSharingPokemon(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = MaterialTheme.colorScheme.error,
        )
        Text(
            text = stringResource(R.string.error_sharing_pokemon),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Preview
@Composable
fun SharePokemonToReceiverDialogPreview() {
    SharePokemonToReceiverDialog(isError = false, text = "", onDismiss = { }, onConfirm = { }, onTokenChange = { })
}

@Preview
@Composable
fun SharePokemonToReceiverDialogErrorPreview() {
    SharePokemonToReceiverDialog(isError = true, text = "", onDismiss = { }, onConfirm = { }, onTokenChange = { })
}
