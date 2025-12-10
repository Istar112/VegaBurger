package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class BorrarPedidoUseCase (private val repositorio: IPedidoRepositorio, private val almacenDatos: AlmacenDatos){
    suspend fun invoke(id: String){
        val tempo=repositorio.getById(id)
        val elementos =repositorio.getAll();
        if(tempo==null) {
            throw IllegalArgumentException("Id no registrado.")
        }
        val tempoDto= tempo.toDTO()
        val eliminado = repositorio.remove(id)
        if (!eliminado){
            throw IllegalStateException("No se pudo eliminar el pedido: $id")
        }
    }
}