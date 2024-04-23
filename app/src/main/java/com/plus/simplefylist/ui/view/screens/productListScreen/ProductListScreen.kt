package com.plus.simplefylist.ui.view.screens.productListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.plus.simplefylist.ui.view.viewModel.ProductListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    listId: String,
    onNavgationToBack: () -> Unit
) {
    val viewModel = koinViewModel<ProductListViewModel>()
    val list = viewModel.getList(listId).collectAsState(initial = null).value
    val productsChecked =
        viewModel.getAllProductCheckedForList(listId).collectAsState(initial = emptyList())
    val listProduct = viewModel.getAllProductForList(listId).collectAsState(initial = emptyList())
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var productId by remember {
        mutableStateOf("")
    }

    var progress = if (listProduct.value.size > 0) {
        productsChecked.value.size.toFloat() / listProduct.value.size.toFloat()
    } else {
        0f
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Column(
                        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = list?.listName ?: "Lista",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LinearProgressIndicator(
                                progress = progress.absoluteValue,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(10.dp),

                                )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(text = "${productsChecked.value.size}/ ${listProduct.value.size} Itens")

                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onNavgationToBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Voltar a tela anterior"
                        )

                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.deleteList(listId)
                            viewModel.deleteProducts(listId)
                        }

                        onNavgationToBack()
                    }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Deletar Lista"
                        )

                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Adicionar Produtos") },
                icon = { Icon(Icons.Filled.Add, contentDescription = list?.listName) },
                onClick = {
                    showBottomSheet = true

                }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp)) {

                if (listProduct.value.isNotEmpty()) {
                    items(listProduct.value) { list ->
                        list.productCategory?.let {
                            ProductCardComposable(
                                productName = list.productName,
                                productCategory = it,
                                productPrice = list.productPrice,
                                productQuant = list.productQuantity.toString(),
                                onClick = {
                                    showBottomSheet = true
                                    productId = list.id

                                },
                                isChecked = list.isChecked,
                                onCheckedChange = { newCheck ->
                                    viewModel.viewModelScope.launch {
                                        viewModel.updateCheck(
                                            list.id,
                                            newCheck
                                        )
                                    }

                                }
                            )
                        }
                    }
                }
            }
        }
        if (showBottomSheet) {
            CadastroProdutoBottomModalComposable(
                onClose = {
                    showBottomSheet = false
                    productId = ""
                },
                listId = listId,
                productId = productId.ifEmpty { null }
            )
        }

    }
}