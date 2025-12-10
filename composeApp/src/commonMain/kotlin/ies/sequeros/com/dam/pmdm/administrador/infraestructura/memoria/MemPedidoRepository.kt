package ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido

class MemPedidoRepository: IPedidoRepositorio {
    override suspend fun add(item: Pedido) {

    }

    override suspend fun remove(item: Pedido): Boolean {
        return false
    }

    override suspend fun remove(id: String): Boolean {
        return false
    }

    override suspend fun update(item: Pedido): Boolean {
        return false
    }

    override suspend fun getAll(): List<Pedido> {
        TODO("Not yet implemented")
    }

    override suspend fun findByName(name: String): Pedido? {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): Pedido? {
        TODO("Not yet implemented")
    }
}