package com.example.ejemplollamarapi.network.product

import com.example.ejemplollamarapi.network.product.model.ProductListResponse

class ProductRepository {
    val api = ProductService()

    suspend fun getAllProducts(): ProductListResponse {
        return api.getAllProducts()
    }

    suspend fun searchProduct(productoBuscar: String) : ProductListResponse {
        return api.searchProduct(productoBuscar)
    }
}