package com.example.ejemplollamarapi.network.product

import com.example.ejemplollamarapi.network.RetrofitHelper
import com.example.ejemplollamarapi.network.product.model.ProductListResponse
import com.example.ejemplollamarapi.network.product.model.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductService {
    val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllProducts(): ProductListResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ProductClient::class.java).getAllProducts()
            return@withContext response.body()!!
        }
    }

    suspend fun searchProduct(productoBuscar: String): ProductListResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ProductClient::class.java).searchProduct(productoBuscar)
            return@withContext response.body()!!
        }
    }

}