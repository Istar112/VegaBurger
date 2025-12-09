package ies.sequeros.com.dam.pmdm.tpv.ui.categorias

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.tpv.TpvViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.productos.TpvProductoCard
import ies.sequeros.com.dam.pmdm.tpv.ui.productos.TpvProductoSeleccionadoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpvProductos(
    viewModel: TpvViewModel,
    onNext: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val productos by viewModel.productosFiltrados.collectAsState()

    val productoSeleccionado by viewModel.productoSeleccionado.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val filteredItems = productos.filter {
        if (searchText.isNotBlank()) {
            it.nombre.contains(searchText, ignoreCase = true)
        } else {
            true
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            placeholder = { Text("Buscar...") },
            modifier = Modifier
                .widthIn(max = 250.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxSize()) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 180.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                items(filteredItems.size) { index ->
                    val item = filteredItems[index]
                    TpvProductoCard(
                        item = item,
                        Modifier.padding(4.dp),
                        onClick = { viewModel.seleccionarProducto(item) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (productoSeleccionado != null) {
                    TpvProductoSeleccionadoCard(
                        viewModel = viewModel,
                        item = productoSeleccionado!!,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /* acción extra */ }
                    )
                } else {
                    Text("Selecciona un producto")
                }
            }
        }
    }

}












