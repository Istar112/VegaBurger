package ies.sequeros.com.dam.pmdm.tpv.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.tpv.TpvViewModel

@Composable
fun CInicioNombre(
    tpvViewModel: TpvViewModel,
    onNext: () -> Unit,
    onExit: () -> Unit,
) {
    tpvViewModel.crearPedidoSiNoExiste()
    val pedidoActual by tpvViewModel.pedidoActual.collectAsState()

    var nombre by remember(pedidoActual?.nombreUsuario) {
        mutableStateOf(pedidoActual?.nombreUsuario ?: "")
    }
    // Sincroniza cuando cambia el pedido
    LaunchedEffect(pedidoActual?.nombreUsuario) {
        if (pedidoActual?.nombreUsuario != nombre) {
            nombre = pedidoActual?.nombreUsuario ?: ""
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){


            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    tpvViewModel.añadirNombrePedido(it) },
                label = { Text("Nombre") },
                singleLine = true,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ){
                Button(onClick = onExit) {
                    Text("Salir")
                }
                // Botones
                Button(onClick = onNext) {
                    Text("Comenzar pedido")
                }
            }

        }



    }
}