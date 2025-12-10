package ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido

data class LineaPedidoUi(
    val id: String,
    val unidades: Int,
    val idProducto: String,
    val idPedido: String,
    val precio: Float
)

fun LineaPedido.toUi() = LineaPedidoUi(
    id = this.id,
    unidades = this.unidades,
    idProducto = this.idProducto,
    idPedido = this.idPedido,
    precio = this.precio
)

fun LineaPedidoDTO.toUi() = LineaPedidoUi(
    id = this.id,
    unidades = this.unidades,
    idProducto = this.idProducto,
    idPedido = this.idPedido,
    precio = this.precio
)

fun LineaPedidoUi.toLineaPedido() = LineaPedido(
    id = this.id,
    unidades = this.unidades,
    idProducto = this.idProducto,
    idPedido = this.idPedido,
    precio = this.precio
)

fun LineaPedidoUi.toDTO() = LineaPedidoDTO(
    id = this.id,
    unidades = this.unidades,
    idProducto = this.idProducto,
    idPedido = this.idPedido,
    precio = this.precio
)