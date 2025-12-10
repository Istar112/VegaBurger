package ies.sequeros.com.dam.pmdm

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.com.dam.pmdm.AppRoutes.LoginAdmin
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
import ies.sequeros.com.dam.pmdm.commons.ui.login.Login
import ies.sequeros.com.dam.pmdm.commons.ui.login.LoginViewModel
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
    val loginViewModel = viewModel { LoginViewModel(dependienteRepositorio) }
    appViewModel.setWindowsAdatativeInfo( currentWindowAdaptiveInfo())

    val navController= rememberNavController()
//para cambiar el modo
    AppTheme(appViewModel.darkMode.collectAsState()) {

        NavHost(
            navController,
            startDestination = AppRoutes.Main
        ) {
            composable(AppRoutes.Main) {
                Principal(

                    {
                    navController.navigate(AppRoutes.LoginAdmin)
                },
                    onDependiente = {
                        navController.navigate(AppRoutes.LoginDependiente)
                    },
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
            composable(AppRoutes.LoginAdmin){
                Login(
                    onNext = {navController.navigate(AppRoutes.Administrador) },
                    onExit = {navController.popBackStack()},
                    onAction = {loginViewModel.loginAdmin()},
                    loginViewModel = loginViewModel,
                    "Nota: Solo puedes iniciar si la cuenta es de tipo admin"
                )

            }
            composable(AppRoutes.LoginDependiente){
                Login(
                    onNext = {navController.navigate(AppRoutes.Dependiente) },
                    onExit = {navController.popBackStack()},
                    onAction = {loginViewModel.loginDependiente()},
                    loginViewModel = loginViewModel,
                    "Nota: Solo puedes entrar si estas en la base de datos registrado," +
                            "Un admin te puede crear la cuenta"
                )
            }

        }
    }

}