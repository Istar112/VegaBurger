package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ListarPedidosUseCase (private val repositorio: IPedidoRepositorio, private val almacenDatos: AlmacenDatos){
    suspend fun invoke( ): List<PedidosDTO>{
        val items= repositorio.getAll().map { it.toDTO() }
        return items
    }
}