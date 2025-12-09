package ies.sequeros.com.dam.pmdm.tpv.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.com.dam.pmdm.tpv.TpvViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.categorias.TpvCategorias
import ies.sequeros.com.dam.pmdm.tpv.ui.categorias.TpvProductos
import ies.sequeros.com.dam.pmdm.tpv.ui.pedidos.TpvPedido


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTpv(
    tpvViewModel: TpvViewModel,
    onNext: () -> Unit,
    onExit: () -> Unit,
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val productos by tpvViewModel.productosFiltrados.collectAsState()
    val productoSeleccionado by tpvViewModel.productoSeleccionado.collectAsState()
    val lineaspedido by tpvViewModel.itemsLineaPedidos.collectAsState()
    val pedidoactual by tpvViewModel.pedidoActual.collectAsState()

    Scaffold(
        topBar = {

            TopAppBar(

                title = {

                    Text("VegaBurguer")

                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = "Restaurante",
                    )
                },
                actions = {
                    IconButton(
                        onClick = {navController.navigate(TpvRoutes.TPVPedido)}
                    ){
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Restaurante",
                        )
                    }
                    Text("Cantidad: ${pedidoactual?.npTotales}")
                    Text("Nombre: ${pedidoactual?.nombreUsuario}")
                    Text(
                        text = "Total: ${pedidoactual?.importeTotal ?: 0.0}€",
                        Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )

                },

            )
        },
        bottomBar = {
            BottomAppBar {
                Row(

                ) {
                    Button(
                        onClick = onExit,
                        modifier = Modifier.padding(16.dp)
                    )
                    {
                        Text("Salir")
                    }

                    Button(
                        onClick = {
                            tpvViewModel.confirmarPedido()
                            onNext},
                        modifier = Modifier.padding(16.dp) ,
                        enabled = lineaspedido.isNotEmpty(),

                        )
                    {
                        Text("Confirmar pedido")
                    }

                }

            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val irAtras = navController.popBackStack()
                    if (!irAtras) {
                        navController.navigate(TpvRoutes.TPVCategorias)
                    }
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        },
        floatingActionButtonPosition = FabPosition.End,

    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = TpvRoutes.TPVCategorias
            ){
                composable(TpvRoutes.TPVCategorias){
                    TpvCategorias(
                        tpvViewModel,
                        onNext = {
                            navController.navigate(TpvRoutes.TPVProductos)
                        },
                        onExit = {
                            navController.popBackStack()
                        },
                    )
                }
                composable(TpvRoutes.TPVProductos){
                    TpvProductos(
                        tpvViewModel,
                        onNext = {},
                        onExit = {},
                    )
                }
                composable(TpvRoutes.TPVPedido){
                    TpvPedido(
                        tpvViewModel,
                        modifier = Modifier.fillMaxSize()


                    )
                }

            }


        }




    }
}