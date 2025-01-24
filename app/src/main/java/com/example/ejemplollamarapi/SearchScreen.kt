package com.example.ejemplollamarapi

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ejemplollamarapi.db.productResponseToProduct
import com.example.ejemplollamarapi.network.product.model.ProductResponse

@Composable
fun SearchScreen(
    productViewModel: ProductViewModel,
    favouriteProductViewModel: FavouriteProductViewModel,
    context: Context
) {
    val isLoading: Boolean by productViewModel.isLoading.observeAsState(initial = false)
    var productoBuscar by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = productoBuscar,
                label = { Text("Buscar producto") },
                onValueChange = { productoBuscar = it },
                trailingIcon = { Icon(Icons.Filled.Search, contentDescription = "Icono buscar") }
            )
            Button(
                onClick = { productViewModel.searchProducts(productoBuscar) },
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Text("Buscar")
            }
        }
        if (isLoading) {
            LoadingScreen()
        } else {
            SearchListView(
                productViewModel.productSearchList.value!!,
                favouriteProductViewModel,
                context
            )
        }
    }
}


@Composable
fun SearchListView(
    searchList: List<ProductResponse>,
    favouriteProductViewModel: FavouriteProductViewModel,
    context: Context
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 25.dp, bottom = 10.dp),
                text = "Productos encontrados",
                fontSize = TextUnit(8f, TextUnitType.Em),
                fontWeight = FontWeight.Bold
            )
        }
        if (searchList.isEmpty()) {
            item{
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = "Lista vacía",
                    fontSize = TextUnit(8f, TextUnitType.Em)
                )
            }
        } else {
            items(searchList) { product ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier.width(270.dp).padding(bottom = 25.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.title,
                            fontSize = TextUnit(5.5f, TextUnitType.Em),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        FilledIconButton(
                            modifier = Modifier.size(25.dp).padding(start = 10.dp),
                            onClick = {
                                Toast.makeText(context, "Producto añadido", Toast.LENGTH_SHORT).show()
                                favouriteProductViewModel.insertOrUpdateFavoriteProduct(
                                    productResponseToProduct(product)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Añadir elemento"
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier.fillMaxSize().padding(start = 5.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(160.dp),
                            model = product.thumbnail,
                            contentDescription = "Imagen del ${product.title}"
                        )
                    }
                }
            }
        }
    }
}