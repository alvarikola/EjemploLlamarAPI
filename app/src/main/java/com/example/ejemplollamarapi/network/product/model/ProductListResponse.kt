package com.example.ejemplollamarapi.network.product.model

import com.google.gson.annotations.SerializedName

data class ProductListResponse (
    @SerializedName("products") var productList: List<ProductResponse>,
    @SerializedName("total") var total: Int,
    @SerializedName("skip") var skip: Int,
    @SerializedName("limit") var limit: Int

)