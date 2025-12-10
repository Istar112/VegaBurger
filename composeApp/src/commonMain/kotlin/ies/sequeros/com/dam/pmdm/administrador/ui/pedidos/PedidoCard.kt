package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.PedidosDTO
import ies.sequeros.com.dam.pmdm.commons.ui.ImagenDesdePath
import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.hombre

@Suppress("UnrememberedMutableState")
@Composable
fun PedidoCard(
    pedido: PedidosDTO,
    onView: (PedidosDTO) -> Unit,
    onEdit: (PedidosDTO) -> Unit = {},
    onDelete: (PedidosDTO) -> Unit,
    onToggleDelivered: (PedidosDTO) -> Unit = {}
) {
    val entregado = try {
        // intenta leer un campo 'entregado' o 'estado' de forma segura
        val field = pedido::class.java.getDeclaredField("entregado")
        field.isAccessible = true
        field.getBoolean(pedido)
    } catch (_: Exception) {
        false
    }

    val cardAlpha by animateFloatAsState(if (entregado) 0.6f else 1f)
    val imgPathState = remember { mutableStateOf("") }
    try {
        val f = pedido::class.java.getDeclaredField("imgPath")
        f.isAccessible = true
        val v = f.get(pedido) as? String
        if (!v.isNullOrBlank()) imgPathState.value = v
    } catch (_: Exception) {
    }

    val borderColor = if (entregado) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.secondary

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .alpha(cardAlpha),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .border(2.dp, borderColor, CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                ImagenDesdePath(imgPathState, Res.drawable.hombre, Modifier.fillMaxWidth())
            }

            Text(
                text = "Pedido: ${pedido.id ?: "-"}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            val clienteText = try {
                val f = pedido::class.java.getDeclaredField("cliente")
                f.isAccessible = true
                f.get(pedido)?.toString() ?: ""
            } catch (_: Exception) { "" }

            if (clienteText.isNotBlank()) {
                Text(text = "Cliente: $clienteText", style = MaterialTheme.typography.bodyMedium)
            }

            val totalText = try {
                val f = pedido::class.java.getDeclaredField("total")
                f.isAccessible = true
                f.get(pedido)?.toString() ?: ""
            } catch (_: Exception) { "" }

            if (totalText.isNotBlank()) {
                Text(text = "Total: $totalText", style = MaterialTheme.typography.bodyMedium)
            }

            AssistChip(
                onClick = {},
                label = { Text(if (entregado) "Entregado" else "Pendiente") },
                leadingIcon = {
                    Icon(
                        if (entregado) Icons.Default.CheckCircle else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = if (entregado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                    )
                },
                colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            )

            HorizontalDivider(Modifier.fillMaxWidth(0.85f), DividerDefaults.Thickness, MaterialTheme.colorScheme.outlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(
                    onClick = { onToggleDelivered(pedido) },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (entregado) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Icon(if (entregado) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = "Toggle Delivered")
                }

                OutlinedIconButton(onClick = { onView(pedido) }) {
                    Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Ver")
                }

                OutlinedIconButton(onClick = { onEdit(pedido) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }

                OutlinedIconButton(
                    onClick = { onDelete(pedido) },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}