package com.plus.simplefylist.navigation

sealed class AppDestination(val route: String) {

    object HomeScreen : AppDestination("home-screen")
    object ProductListScreen : AppDestination("product-list-screen")

}
