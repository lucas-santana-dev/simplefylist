package com.plus.simplefylist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plus.simplefylist.ui.view.screens.homeScreen.HomeScreen
import com.plus.simplefylist.ui.view.screens.productListScreen.ProductListScreen


@Composable
fun SimplefyListNavHost(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = AppDestination.HomeScreen.route
    ) {
        composable(
            AppDestination.HomeScreen.route
        ) {
            HomeScreen(
                onClickAbrirCadastroDeList = { lista ->
                    navController.navigate("${AppDestination.ProductListScreen.route}/${lista.id}")
                }
            )
        }

        composable(
            "${AppDestination.ProductListScreen.route}/{listId}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments!!.getString("listId")
            ProductListScreen(
                listId = id ?: "",
                onNavgationToBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}