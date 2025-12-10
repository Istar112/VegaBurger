package ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria

import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio


class MemPedidoRepository: IPedidoRepositorio {

    private val items=hashMapOf<String, Pedido>();
    init{

    }
    override suspend  fun add(item: Pedido) {
        if( !items.containsKey(item.id)){
            items.put(item.id!!,item)
        }else{
            throw IllegalArgumentException("ALTA:El usuario con id:"+item.id+" ya existe")
        }
    }

    override suspend  fun remove(item: Pedido): Boolean {
        return this.remove(item.id!!)
    }

    override suspend fun remove(id: String): Boolean {
        if( items.containsKey(id)){
            items.remove((id))
            return true
        }else{
            throw IllegalArgumentException("BORRADO:" +
                    " El usuario con id:"+id+" NO  existe")
        }
    }

    override suspend fun update(item: Pedido): Boolean {
        if( items.containsKey(item.id)){
            items[item.id!!]=item
            return true
        }else{
            throw IllegalArgumentException("ACTUALIZACION: " +
                    "El pedido con id:"+item.id+" NO existe")
        }
    }

    override suspend fun getAll(): List<Pedido> {
        return this.items.values.toList();
    }

    override suspend fun findByName(name: String): Pedido? {
        return this.items.values.firstOrNull { it.nombreUsuario.equals(name) };
    }

    override suspend fun getById(id: String): Pedido? {
        return this.items[id];
    }



}