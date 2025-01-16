package com.example.ejemplollamarapi

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.ejemplollamarapi.network.product.model.ProductResponse

@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    context: Context,
    innerPaddingValues: PaddingValues
) {
    val isLoading: Boolean by productViewModel.isLoading.observeAsState(initial = true)
    if (isLoading) {
        productViewModel.getAllProducts()
        LoadingScreen()
    } else {
        CompleteProductListScreen(productViewModel.productList.value!!)
    }
}


@Composable
fun CompleteProductListScreen(productList: List<ProductResponse>) {
    Text("Ya cargo")
}