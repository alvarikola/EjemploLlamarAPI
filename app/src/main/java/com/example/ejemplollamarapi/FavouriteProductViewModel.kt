package com.example.ejemplollamarapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplollamarapi.db.Product
import com.example.ejemplollamarapi.db.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavouriteProductViewModel(private val dao: ProductDao): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavoriteProductList(): Flow<List<Product>> {
        return dao.getFavouriteProductList()
    }

    fun insertOrUpdateFavoriteProduct(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            dao.upsertProduct(product)
            _isLoading.value = false
        }
    }

    fun deleteFavoriteProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            dao.deleteFavouriteProduct(productId)
            _isLoading.value = false
        }
    }
}