package ies.sequeros.com.dam.pmdm.dependiente.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.com.dam.pmdm.dependiente.DependienteRoutes
import ies.sequeros.com.dam.pmdm.dependiente.ui.pedidos.Pedidos
import ies.sequeros.com.dam.pmdm.dependiente.ui.pedidos.PedidosViewModel


@Composable
fun MainDependiente(
    viewModel: PedidosViewModel,
    onExit: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.8f).fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // NavHost arriba
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = DependienteRoutes.Pedidos,
                modifier = Modifier.weight(1f)
            ) {
                composable(DependienteRoutes.Pedidos) {
                    Pedidos(pedidosViewModel = viewModel)
                }
            }

            // Botón abajo
            Button(onClick = { onExit() }) {
                Text("Salir")
            }
        }

    }
}
