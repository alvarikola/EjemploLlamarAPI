package com.example.ejemplollamarapi.network.product

import com.example.ejemplollamarapi.network.product.model.ProductListResponse
import com.example.ejemplollamarapi.network.product.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductClient {
    @GET("/products")
    suspend fun getAllProducts(): Response<ProductListResponse>

    @GET("/product/search")
    suspend fun searchProduct(@Query("q") queryString: String): Response<ProductListResponse>

}