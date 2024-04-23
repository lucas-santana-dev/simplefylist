package com.plus.simplefylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.plus.simplefylist.navigation.SimplefyListNavHost
import com.plus.simplefylist.ui.theme.SimplefyListTheme
import com.plus.simplefylist.ui.view.screens.homeScreen.HomeScreen
import com.plus.simplefylist.ui.view.screens.productListScreen.ProductListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            SimplefyListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    SimplefyListNavHost(navController = navController)
                }
            }
        }
    }
}

