package com.plus.simplefylist.ui.view.screens.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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
            CenterAlignedTopAppBar(
                title = {
                   Box(modifier = Modifier.size(150.dp)){
                       Image(
                           painter = painterResource(id = R.drawable.simplefy_logo),
                           contentDescription = null,
                           colorFilter = ColorFilter.tint(Color.White),
                           contentScale = ContentScale.Fit,


                       )
                   }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAlertDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.content_description_icon_add_lista_de_compras),
                    tint = Color.White
                )
            }
        },

        ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp)) {
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
                    Image(
                        painter = painterResource(id = R.drawable.no_lists),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp)
                            .alpha(0.5f)
                    )
                    Text(
                        text = stringResource(id = R.string.abc_sem_lista_de_compras),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,

                        )
                    Text(
                        text = stringResource(id = R.string.abc_clique_abaixo_para_criar_lista),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
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