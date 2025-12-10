package ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido.LineaPedidoViewModel

@Composable
fun LineaPedidoForm(
    pedidosViewModel: LineaPedidoViewModel,
    initial: LineaPedidoDTO? = null,
    onSave: (CrearLineaPedidoCommand) -> Unit,
    onCancel: () -> Unit = {}
) {
    // campos locales
    var productoId by remember { mutableStateOf(initial?.idProducto ?: "") }
    var unidadesStr by remember { mutableStateOf(initial?.unidades?.toString() ?: "1") }
    var precioStr by remember { mutableStateOf(initial?.precio?.toString() ?: "0") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = productoId,
            onValueChange = { productoId = it },
            label = { Text("Producto Id") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = unidadesStr,
            onValueChange = { unidadesStr = it.filter { ch -> ch.isDigit() } },
            label = { Text("Unidades") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precioStr,
            onValueChange = { precioStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onCancel, colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onSurface)) {
                Text("Cancelar")
            }

            Spacer(Modifier.width(8.dp))

            Button(onClick = {
                val unidades = unidadesStr.toIntOrNull() ?: 1
                val precio = precioStr.toFloatOrNull() ?: 0f
                val command = CrearLineaPedidoCommand(
                    pedidoId = initial?.idPedido ?: pedidosViewModel.selected.value?.idPedido ?: "",
                    productoId = productoId,
                    unidades = unidades,
                    precio = precio
                )
                onSave(command)
            }) {
                Icon(Icons.Default.Save, contentDescription = "Guardar")
                Spacer(Modifier.width(8.dp))
                Text(if (initial == null) "Crear" else "Actualizar")
            }
        }
    }
}