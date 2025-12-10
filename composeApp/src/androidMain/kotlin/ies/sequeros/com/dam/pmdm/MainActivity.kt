package ies.sequeros.com.dam.pmdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FileProductoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.FileDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.MemCategoriaRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.MemLineaPedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.MemPedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.MemProductoRepository
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //se crea el almacen para el json
        val almacenDatos:AlmacenDatos=  AlmacenDatos(this)
        //se le pasa al repositorio
        val dependienteRepositorio: IDependienteRepositorio =
            FileDependienteRepository(almacenDatos)
        val categoriaRepository: ICategoriaRepositorio= MemCategoriaRepository()
        val productosRepositorio: IProductoRepositorio = MemProductoRepository()
        val lneasPedidoRepositorio: ILineaPedidoRepositorio = MemLineaPedidoRepository()
        val pedidosRepositorio: IPedidoRepositorio = MemPedidoRepository()

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            //se crean almacenes de datos y de imagenes propias de la plataforma y se
            //pasan a la aplicación,
            val almacenImagenes:AlmacenDatos=  AlmacenDatos(this)

            App(
                dependienteRepositorio,
                categoriaRepository,
                productosRepositorio,
                lneasPedidoRepositorio,
                pedidosRepositorio,
                almacenImagenes
            )
        }
    }
}

