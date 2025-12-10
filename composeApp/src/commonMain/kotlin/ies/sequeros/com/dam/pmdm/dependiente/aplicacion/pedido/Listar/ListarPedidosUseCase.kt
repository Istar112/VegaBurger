package ies.sequeros.com.dam.pmdm.dependiente.aplicacion.pedido.Listar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.toDTO

class ListarPedidosUseCase(
    private val repositorioPedidos : IPedidoRepositorio,
){
    suspend fun invoke () : List<PedidoDTO>{
        val items= repositorioPedidos.getAll().map { it.toDTO()}
        return items
    }
}