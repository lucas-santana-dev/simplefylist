package com.plus.simplefylist.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AlertDialogCadastrarLista(
    isVisible: Boolean = false,
    onCancel: () -> Unit,
    onSave: (String) -> Unit,
) {

    var nomeList by remember {
        mutableStateOf("")
    }

    if (isVisible){
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(text = "Cadastrar Lista de compras")
            },
            text = {
                Column {
                    Text(text = "Qual o nome para sua lista?")
                    OutlinedTextField(value = nomeList, onValueChange = { newName ->
                        nomeList = newName
                    })
                }
            },
            onDismissRequest = {
                onCancel()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onSave(nomeList)
                    }
                ) {
                    Text(text = "Salvar")
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlertDialogCadastrarLista() {
    AlertDialogCadastrarLista(
        isVisible = true,
        onCancel = { /* Implemente a ação de cancelar */ },
        onSave = { /* Implemente a ação de salvar */ }
    )
}