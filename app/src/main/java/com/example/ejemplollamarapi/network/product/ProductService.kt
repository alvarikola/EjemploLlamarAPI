package com.example.ejemplollamarapi.network.product

import com.example.ejemplollamarapi.network.RetrofitHelper
import com.example.ejemplollamarapi.network.product.model.ProductListResponse
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

}