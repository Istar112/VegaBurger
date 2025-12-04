package ies.sequeros.com.dam.pmdm.administrador.infraestructura

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedidos.BBDDRepositorioPedidosJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido

class BBDDPedidoRepository (
    private val bbddRepositorioPedidosJava : BBDDRepositorioPedidosJava
) : IPedidoRepositorio{
    override suspend fun add(item: Pedido) {
        bbddRepositorioPedidosJava.add(item)
    }

    override suspend fun remove(item: Pedido): Boolean {
        bbddRepositorioPedidosJava.remove(item)
        return true
    }

    override suspend fun remove(id: String): Boolean {
        bbddRepositorioPedidosJava.remove(id)
        return true
    }

    override suspend fun update(item: Pedido): Boolean {
        bbddRepositorioPedidosJava.update(item)
        return true
    }

    override suspend fun getAll(): List<Pedido> {
        return bbddRepositorioPedidosJava.getAll()
    }

    override suspend fun findByName(name: String): Pedido? {
        return bbddRepositorioPedidosJava.findByName(name)
    }

    override suspend fun getById(id: String): Pedido? {
        return bbddRepositorioPedidosJava.getById(id)
    }
}