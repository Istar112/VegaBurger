package ies.sequeros.com.dam.pmdm.tpv.ui.categorias

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.tpv.TpvViewModel
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.categorias.CategoriaDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpvCategorias(
    viewModel: TpvViewModel,
    onNext: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val categorias by viewModel.itemsCategoria.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val filteredItems = categorias.filter {
        if (searchText.isNotBlank()) {
            it.nombre.contains(searchText, ignoreCase = true)
        } else {
            true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cargarCategorias()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Buscador centrado
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                placeholder = { Text("Buscar...") },
                modifier = Modifier

                    .widthIn(max = 250.dp) // ancho fijo para centrar
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 180.dp), // Tamaño adaptativo
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {

                items(filteredItems.size) { index ->
                    val item = filteredItems[index]
                    TpvCategoriaCard(
                        item = item,

                        onClick = {
                            viewModel.seleccionarCategoria(item)
                            onNext()
                        },

                    )

                }
            }

        }
    }

}






