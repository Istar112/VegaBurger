package ies.sequeros.com.dam.pmdm.tpv.ui.pedidos


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ies.sequeros.com.dam.pmdm.tpv.TpvViewModel

@Composable
fun TpvPedido(viewModel: TpvViewModel, modifier: Modifier = Modifier) {
    val lineas by viewModel.itemsLineaPedidos.collectAsState()
    val productosMap by viewModel.todosProductosMap.collectAsState()

    LazyColumn {
        items(lineas, key = { it.id }) { linea ->
            val productoDTO = productosMap[linea.idProducto]

            if (productoDTO != null) {
                TpvLineaPedidoCard(
                    viewModel,
                    linea,
                    { viewModel.añadirProductoLineaPedido(productoDTO) },
                    { viewModel.eliminarProductoLineaPedido(productoDTO) }
                )
            }
        }
    }

}

