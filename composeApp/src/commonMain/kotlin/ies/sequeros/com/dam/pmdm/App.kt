package ies.sequeros.com.dam.pmdm

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.com.dam.pmdm.administrador.AdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio

import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministrador
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriasViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.DependientesViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductosViewModel
import ies.sequeros.com.dam.pmdm.tpv.TpvViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.CInicioNombre
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpv

@Suppress("ViewModelConstructorInComposable")
@Composable

fun App(
    dependienteRepositorio : IDependienteRepositorio,
    categoriaRepository: ICategoriaRepositorio,
    productosRepositorio: IProductoRepositorio,
    lineaPedidoRepositorio: ILineaPedidoRepositorio,
    pedidosRepositorio: IPedidoRepositorio,
    almacenImagenes:AlmacenDatos    
) {

    //view model
    val appViewModel= viewModel {  AppViewModel() }
    val mainViewModel= remember { MainAdministradorViewModel() }
    val administradorViewModel= viewModel { AdministradorViewModel() }
    val dependientesViewModel = viewModel{ DependientesViewModel(
        dependienteRepositorio, almacenImagenes
    )}
    val productosViewModel = viewModel { ProductosViewModel(
       productosRepositorio, almacenImagenes
    ) }

    val categoriasViewModel = viewModel{ CategoriasViewModel(
        categoriaRepository, almacenImagenes
    )}
    val tpvViewModel = viewModel {
        TpvViewModel(
            categoriaRepository,
            productosRepositorio,
            lineaPedidoRepositorio,
            pedidosRepositorio,
            dependienteRepositorio,
            almacenImagenes)
    }

    appViewModel.setWindowsAdatativeInfo( currentWindowAdaptiveInfo())
    val navController= rememberNavController()
//para cambiar el modo
    AppTheme(appViewModel.darkMode.collectAsState()) {

        NavHost(
            navController,
            startDestination = AppRoutes.Main
        ) {
            composable(AppRoutes.Main) {
                Principal({
                    navController.navigate(AppRoutes.Administrador)
                },
                    onDependiente = {},
                    onTPV = {
                        navController.navigate(AppRoutes.TPVnombre){
                        }

                    })
            }
            composable (AppRoutes.Administrador){
                MainAdministrador(
                    appViewModel,
                    mainViewModel,
                    administradorViewModel,
                    dependientesViewModel,
                    categoriasViewModel,
                    productosViewModel,
                     {
                    navController.popBackStack()
                })
            }
            composable(AppRoutes.TPVnombre){
                CInicioNombre(
                    tpvViewModel,
                    onNext = {
                        navController.navigate(AppRoutes.TPVmain)
                    },
                    onExit = {
                        navController.popBackStack()
                    }
                )
            }
            composable(AppRoutes.TPVmain){
                MainTpv(
                    tpvViewModel,
                    onNext = {},
                    onExit = {
                        navController.popBackStack()
                    }
                )


            }
//            composable (AppRoutes.TPVCategorias){
//                CCategorias(
//                    tpvViewModel,
//                    onNext = {
//                        navController.navigate(AppRoutes.TPVProductos)
//                    },
//                    onExit = {
//                        navController.popBackStack()
//                    },
//
//
//                )
//            }
//            composable(AppRoutes.TPVProductos){
//                TpvProductos(
//                    tpvViewModel
//                ,{ navController.popBackStack() },
//                    {navController.popBackStack()}
//                )
//
//            }


        }
    }

}