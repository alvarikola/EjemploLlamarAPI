package com.example.ejemplollamarapi.network.product.model

import com.example.ejemplollamarapi.network.product.ProductService

class ProductRepository {
    val api = ProductService()

    suspend fun getAllProducts(): ProductListResponse {
        return api.getAllProducts()
    }
}