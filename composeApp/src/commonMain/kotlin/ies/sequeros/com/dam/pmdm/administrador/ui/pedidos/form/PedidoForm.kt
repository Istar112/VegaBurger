package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.form

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.PedidosDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidosViewModel


@Composable
fun PedidoForm(
    pedidosViewModel: PedidosViewModel,
    onSubmit: (cliente: String, total: String) -> Unit,
    onCancel: () -> Unit = {}
) {

    val selected by pedidosViewModel.selected.collectAsState()

    var cliente by remember {
        mutableStateOf(
            try {
                selected?.let {
                    val f = it::class.java.getDeclaredField("cliente")
                    f.isAccessible = true
                    f.get(it)?.toString() ?: ""
                } ?: ""
            } catch (e: Exception) { "" }
        )
    }
    var total by remember {
        mutableStateOf(
            try {
                selected?.let {
                    val f = it::class.java.getDeclaredField("total")
                    f.isAccessible = true
                    f.get(it)?.toString() ?: ""
                } ?: ""
            } catch (e: Exception) { "" }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = cliente,
            onValueChange = { cliente = it },
            label = { Text("Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = total,
            onValueChange = { total = it },
            label = { Text("Total") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Text("Cancelar")
            }

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = { onSubmit(cliente.trim(), total.trim()) },
                colors = ButtonDefaults.buttonColors()
            ) {
                Icon(Icons.Default.Save, contentDescription = "Guardar")
                Spacer(Modifier.width(8.dp))
                Text(if (selected == null) "Crear" else "Actualizar")
            }
        }
    }
}