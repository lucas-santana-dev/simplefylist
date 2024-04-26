package com.plus.simplefylist.ui.view.screens.productListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.plus.simplefylist.R
import com.plus.simplefylist.ui.view.viewModel.CadastroProdutoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroProdutoBottomModalComposable(
    onClose: () -> Unit,
    listId: String,
    productId: String? = null
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val viewModel = koinViewModel<CadastroProdutoViewModel>()
    var nomeProduto by remember {
        mutableStateOf("")
    }
    var quantidade by remember {
        mutableStateOf("")
    }
    var categoria by remember {
        mutableStateOf("")
    }
    var preco by remember {
        mutableStateOf("")
    }
    if (productId?.isNotEmpty() == true) {
        val product = viewModel.getProduct(productId).collectAsState(initial = null).value
        nomeProduto = product?.productName ?: ""
        quantidade = product?.productQuantity.toString()
        categoria = product?.productCategory.toString()
        preco = product?.productPrice.toString()
    }
    var vazio by remember {
        mutableStateOf(false)
    }
    ModalBottomSheet(
        onDismissRequest = {
            onClose()
        },
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.title_cadastro_produto),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 5.dp),
                    label = { Text(text = stringResource(id = R.string.label_produto)) },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_produto), maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },

                    value = nomeProduto,
                    onValueChange = { newProduto ->
                        nomeProduto = newProduto
                        if (nomeProduto.isEmpty()) {
                            vazio = true
                        }
                    },
                    isError = vazio
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    label = {
                        Text(text = stringResource(id = R.string.label_quantidade))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_quantidade),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    value = quantidade,
                    onValueChange = { newQuantidade ->
                        quantidade = newQuantidade
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword)

                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 5.dp),
                    label = {
                        Text(text = stringResource(id = R.string.label_categoria))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_categoria),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    value = categoria,
                    onValueChange = { newCategoria ->
                        categoria = newCategoria
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(id = R.string.label_preco)) },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_preco),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    value = preco,
                    onValueChange = { newPreco ->
                        preco = newPreco
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            if (nomeProduto.isNotEmpty()) {
                                viewModel.viewModelScope.launch {
                                    viewModel.adicionarProduto(
                                        nomeProduto = nomeProduto,
                                        quantidade = quantidade,
                                        categoria = categoria,
                                        preco = preco,
                                        listId = listId,
                                        productId = productId
                                    )

                                }
                            }
                            onClose()
                        }
                    }
                }) {
                    Text(stringResource(id = R.string.btn_text_salvar_produto))
                }
            }
        }
    }
}
