package ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.LineaPedidoDTO

@Composable
fun LineaPedidoCard(
    item: LineaPedidoDTO,
    onView: (LineaPedidoDTO) -> Unit = {},
    onDelete: (LineaPedidoDTO) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Producto: ${item.idProducto}", style = MaterialTheme.typography.titleMedium)
            Text(text = "U: ${item.unidades}  •  Precio: ${item.precio} €", style = MaterialTheme.typography.bodyMedium)
            HorizontalDivider(Modifier.fillMaxWidth(0.95f), DividerDefaults.Thickness, MaterialTheme.colorScheme.outlineVariant)
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                AssistChip(onClick = {}, label = { Text("${item.unidades * item.precio} €") }, leadingIcon = { Icon(Icons.Default.Visibility, contentDescription = null) }, colors = AssistChipDefaults.assistChipColors())
                OutlinedIconButton(onClick = { onView(item) }, colors = IconButtonDefaults.iconButtonColors()) {
                    Icon(Icons.Default.Visibility, contentDescription = "Ver")
                }
                OutlinedIconButton(onClick = { onDelete(item) }, colors = IconButtonDefaults.iconButtonColors()) {
                    Icon(Icons.Default.Delete, contentDescription = "Borrar")
                }
            }
        }
    }
}