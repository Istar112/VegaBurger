package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.PedidosDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel

@Composable
fun Pedidos(
    mainAdministradorViewModel: MainAdministradorViewModel,
    pedidosViewModel: PedidosViewModel,
    onSelectItem: (PedidosDTO?) -> Unit
) {
    val items by pedidosViewModel.items.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val filteredItems = items.filter { pedido ->
        if (searchText.isNotBlank()) {
            val idMatch = pedido.id?.contains(searchText, ignoreCase = true) ?: false
            val clienteMatch = try {
                val f = pedido::class.java.getDeclaredField("cliente")
                f.isAccessible = true
                (f.get(pedido) as? String)?.contains(searchText, ignoreCase = true) ?: false
            } catch (_: Exception) {
                false
            }
            idMatch || clienteMatch
        } else {
            true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Buscar pedidos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )

            Spacer(Modifier.width(8.dp))

            OutlinedButton(
                onClick = {
                    pedidosViewModel.setSelectedPedido(null)
                    onSelectItem(null)
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 512.dp),
            modifier = Modifier.fillMaxWidth(),
            content = {
                itemsIndexed(filteredItems) { _, pedido ->
                    PedidoCard(
                        pedido = pedido,
                        onView = {
                            pedidosViewModel.setSelectedPedido(it)
                            onSelectItem(it)
                        },
                        onEdit = {
                            pedidosViewModel.setSelectedPedido(it)
                            onSelectItem(it)
                        },
                        onDelete = {
                            pedidosViewModel.delete(it)
                        },
                        onToggleDelivered = {
                            pedidosViewModel.setSelectedPedido(it)
                            onSelectItem(it)
                        }
                    )
                }
            }
        )
    }
}