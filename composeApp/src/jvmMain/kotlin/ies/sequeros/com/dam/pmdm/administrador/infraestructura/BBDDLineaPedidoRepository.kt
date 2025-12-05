package ies.sequeros.com.dam.pmdm.administrador.infraestructura

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido.BBDDRepositorioLineaPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido

class BBDDLineaPedidoRepository (
    private val bbddRepositorioLineaPedidosJava: BBDDRepositorioLineaPedidoJava
): ILineaPedidoRepositorio {
    override suspend fun add(item: LineaPedido) {
        bbddRepositorioLineaPedidosJava.add(item)
    }

    override suspend fun remove(item: LineaPedido): Boolean {
        bbddRepositorioLineaPedidosJava.remove(item)
        return true
    }

    override suspend fun remove(id: String): Boolean {
        bbddRepositorioLineaPedidosJava.remove(id)
        return true
    }

    override suspend fun update(item: LineaPedido): Boolean {
        bbddRepositorioLineaPedidosJava.update(item)
        return true
    }

    override suspend fun getAll(): List<LineaPedido> {
        return bbddRepositorioLineaPedidosJava.getAll()
    }

    override suspend fun findByName(name: String): LineaPedido? {
        return bbddRepositorioLineaPedidosJava.findByName(name)
    }

    override suspend fun getById(id: String): LineaPedido? {
        return bbddRepositorioLineaPedidosJava.getById(id)
    }
}