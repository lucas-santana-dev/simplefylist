package com.plus.simplefylist.ui.view.screens.productListScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.plus.simplefylist.R
import com.plus.simplefylist.data.entities.ProductEntity
import com.plus.simplefylist.ui.composables.ProgressBarr
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

    var totaldacompra = viewModel.calcularTotalLista(listId).collectAsState(initial = 0.00)
    val formattedTotal = String.format("%.2f", totaldacompra.value)

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
                        ProgressBarr(progress, productsChecked, listProduct)
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
                },
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()){
                    Text(text = "Total da compra: R$ $formattedTotal")

                }
            }
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
                }else{
                    item{
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sem_produtos),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(250.dp)
                                    .alpha(0.5f)
                            )
                            Text(
                                text = stringResource(id = R.string.abc_sem_produtos_na_lista),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,

                                )
                            Text(
                                text = stringResource(id = R.string.abc_clique_abaixo_para_adicionar_um_produto),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
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

