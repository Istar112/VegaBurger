package ies.sequeros.com.dam.pmdm.dependiente.ui.pedidos

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun Pedidos(
    pedidosViewModel: PedidosViewModel,

) {

    val pedidos by pedidosViewModel.pedidos.collectAsState()

    // Leemos los pedidos que van haciendo los clientes
    LaunchedEffect(Unit) {
        pedidosViewModel.cargarPedidos()
    }

    // mostramos las cards
    LazyColumn {
        items(pedidos) { pedido ->
            PedidoCard(
                pedido
            )
        }
    }




}