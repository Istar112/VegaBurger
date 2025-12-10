package ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio


import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido

class MemLineaPedidoRepository: ILineaPedidoRepositorio {

    private val items=hashMapOf<String, LineaPedido>();
    init{
        /* val u1= Producto("02830d95-5ada-403d-9f66-c6c5591240d8","Paco","inventado@gmail.com","1234","ara",true,true)
          items.put(u1.id!!,u1)
          val u2= Producto("0faea46f-b745-4fac-8048-2de194360332","Pedro","pedro@gmail.com","1234","midori",true,false)
          items.put(u2.id!!,u2)
          val u3= Producto("f5be7eab-dbeb-4c54-870a-94c680ae6266","Maria","maria@gmail.com","1234","dr",true,false)
          items.put(u3.id!!,u3)*/
    }
    override suspend  fun add(item: LineaPedido) {
        if( !items.containsKey(item.id)){
            items.put(item.id!!,item)
        }else{
            throw IllegalArgumentException("ALTA:El LineaPedido con id:"+item.id+" ya existe")
        }
    }

    override suspend  fun remove(item: LineaPedido): Boolean {
        return this.remove(item.id!!)
    }

    override suspend fun remove(id: String): Boolean {
        if( items.containsKey(id)){
            items.remove((id))
            return true
        }else{
            throw IllegalArgumentException("BORRADO:" +
                    " El LineaPedido con id:"+id+" NO  existe")
        }
    }

    override suspend fun getByIdProducto(id: String): LineaPedido? {
        return this.items.values.firstOrNull { it.idProducto.equals(id) };
    }

    override suspend fun update(item: LineaPedido): Boolean {
        if( items.containsKey(item.id)){
            items[item.id!!]=item
            return true
        }else{
            throw IllegalArgumentException("ACTUALIZACION: " +
                    "El LineaPedido con id:"+item.id+" NO existe")
        }
    }

    override suspend fun getAll(): List<LineaPedido> {
        return this.items.values.toList();
    }


    override suspend fun findByName(name: String): LineaPedido? {
        return null
    }

    override suspend fun getById(id: String): LineaPedido? {
        return this.items[id];
    }

}