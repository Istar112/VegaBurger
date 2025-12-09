package ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.productos.ProductoDTO



class CrearLineaPedidoUseCase(
    private val repositorio: ILineaPedidoRepositorio,
    private val almacenDatos: AlmacenDatos
) {
    suspend fun invoke(producto: ProductoDTO, idPedido: String) : LineaPedidoDTO {
        val lineaPedidoexistente = repositorio.getByIdProducto(producto.id)
        return if (lineaPedidoexistente != null) {
            val lineaPedidoActualizada = lineaPedidoexistente.copy(
                unidades = lineaPedidoexistente.unidades + 1 ,
                precio = lineaPedidoexistente.precio + producto.precio ,
            )
            repositorio.update(lineaPedidoActualizada)
            LineaPedidoDTO(
                id = lineaPedidoexistente.id, //el mismo
                unidades = lineaPedidoActualizada.unidades, // actualizamos
                idProducto = lineaPedidoexistente.idProducto,
                idPedido = lineaPedidoexistente.idPedido,// el mismo
                precio = lineaPedidoActualizada.precio// actualizamos
            )
        }else{
            var lineaPedidoNueva = LineaPedido(
                id = generateUUID(),
                unidades = 1,
                idProducto = producto.id,
                idPedido = idPedido,
                precio = producto.precio,

                )
            val add = repositorio.add(
               lineaPedidoNueva
            )
            LineaPedidoDTO(
                id = lineaPedidoNueva.id,
                unidades = 1,
                idProducto = producto.id,
                idPedido = lineaPedidoNueva.idPedido,
                precio = producto.precio
            )
        }


    }
}