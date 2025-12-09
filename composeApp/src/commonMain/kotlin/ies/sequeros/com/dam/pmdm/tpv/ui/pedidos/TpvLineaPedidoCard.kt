package ies.sequeros.com.dam.pmdm.tpv.ui.pedidos

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.commons.ui.ImagenDesdePath
import ies.sequeros.com.dam.pmdm.tpv.TpvViewModel
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.LineaPedidoDTO
import vegaburguer.composeapp.generated.resources.hombre

@Composable
fun TpvLineaPedidoCard(
    viewModel: TpvViewModel,
    lineaPedido: LineaPedidoDTO,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp).padding(16.dp),

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val producto = viewModel.todosProductosMap.value[lineaPedido.idProducto]

            val path = remember(producto?.imgPath) {
                mutableStateOf(producto?.imgPath.orEmpty())
            }

            // Imagen del producto
            path.let { path ->
                Box(
                    modifier = Modifier
                        .size(50.dp).
                            clip(CircleShape)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    ImagenDesdePath(
                        path = path,
                        vegaburguer.composeapp.generated.resources.Res.drawable.hombre,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Nombre, unidades y precio
            Row(
                modifier = Modifier.weight(1f), // ocupa espacio disponible
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = producto?.nombre ?: "Producto desconocido",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "U: ${lineaPedido.unidades}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Precio: ${lineaPedido.precio}€",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Botones + y -
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onAdd) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir")
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Remove, contentDescription = "Quitar")
                }
            }
        }
    }
}



