package com.example.ejemplollamarapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ejemplollamarapi.ui.theme.EjemploLlamarAPITheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.ejemplollamarapi.db.ProductDatabase

class MainActivity : ComponentActivity() {
    val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = ProductDatabase::class.java,
            name = "product.db"
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjemploLlamarAPITheme(dynamicColor = false) {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomAppBar (
                            actions = {
                                BottomBarItems(navController)
                            }
                        )
                    }
                ) { innerPadding ->
                    val productViewModel: ProductViewModel = viewModel()
                    val favouriteProductViewModel: FavouriteProductViewModel by viewModels<FavouriteProductViewModel>(
                        factoryProducer = {
                            object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return FavouriteProductViewModel(db.dao) as T
                                }
                            }
                        }
                    )
                    val context = LocalContext.current
                    NavHost(
                        navController = navController,
                        startDestination = "product_list_screen",
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        composable(route = "product_list_screen") {
                            ProductListScreen(productViewModel, context, favouriteProductViewModel, innerPadding)
                        }
                        composable(route = "favourite_list_screen") {
                            FavouriteListScreen(favouriteProductViewModel, context)
                        }
                        composable(route = "search_screen") {
                            SearchScreen(productViewModel, favouriteProductViewModel, context)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun BottomBarItems(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = {navController.navigate("product_list_screen")}
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Home,
                contentDescription = "Lista de productos"
            )
        }
        IconButton(
            onClick = {navController.navigate("favourite_list_screen")}
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Lista de favoritos"
            )
        }
        IconButton(
            onClick = {navController.navigate("search_screen")}
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Search,
                contentDescription = "Lista de favoritos"
            )
        }
    }
}