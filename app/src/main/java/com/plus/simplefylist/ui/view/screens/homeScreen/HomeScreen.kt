package com.plus.simplefylist.ui.view.screens.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
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
import com.plus.simplefylist.data.entities.ListEntity
import com.plus.simplefylist.ui.composables.AlertDialogCadastrarLista
import com.plus.simplefylist.ui.view.viewModel.HomeScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClickAbrirCadastroDeList: (ListEntity) -> Unit,
) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val allLists by viewModel.allLists.collectAsState(initial = emptyList())

    var showAlertDialog by remember {
        mutableStateOf(
            false
        )
    }

    var listname by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_app_bar))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp),
                    )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showAlertDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.content_description_icon_add_lista_de_compras))
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer

    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()) {
            LazyColumn {
                if (allLists.isNotEmpty()) {
                    items(allLists) { list ->
                        ListCardComposable(listName = list.listName,
                            onClick = {
                                onClickAbrirCadastroDeList(list)
                            })
                    }
                }
            }
            if (allLists.isEmpty()) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.abc_sem_lista_de_compras),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,

                        )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_layers_clear_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp)
                            .alpha(0.5f)
                    )
                    Text(
                        text = stringResource(id = R.string.abc_clique_abaixo_para_criar_lista),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )

                }
            }
        }
        AlertDialogCadastrarLista(
            isVisible = showAlertDialog,
            onCancel = { showAlertDialog = false },
            onSave = { nomeList ->
                listname = nomeList

                viewModel.viewModelScope.launch {
                    if (nomeList != "") {
                        val novaLista = viewModel.saveList(nomeList)
                        novaLista?.let { lista ->
                            onClickAbrirCadastroDeList(lista)
                        }
                    }
                }

                showAlertDialog = false
            }

        )

    }
}