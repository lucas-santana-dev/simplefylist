package com.plus.simplefylist.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun AlertDialogConfirmacao(
    isVisible: Boolean = false,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {

    if (isVisible){
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(text = "Atenção")
            },
            text = {
                Column {
                    Text(text = "Deseja realmente excluir a lista de compras e todos itens nela?")

                }
            },
            onDismissRequest = {
                onCancel()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                       onConfirm()
                    }
                ) {
                    Text(text = "Sim, quero excluir")
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text(text = "Não")
                }
            }
        )
    }
}