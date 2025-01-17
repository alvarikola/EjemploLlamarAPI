package com.example.ejemplollamarapi.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {
    @Upsert
    suspend fun upsertProduct(model: Product)

    @Query("SELECT * FROM product")
    fun getFavouriteProductList(): Flow<List<Product>>

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun deleteFavouriteProduct(id: Int): Unit
}