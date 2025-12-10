package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido

fun LineaPedido.toDTO(path: String = "") = LineaPedidoDTO(
    id = this.id,
    unidades = this.unidades,
    idProducto = this.idProducto,
    idPedido = this.idPedido,
    precio = this.precio
)

fun LineaPedidoDTO.toLineaPedido() = LineaPedido(
    id = this.id,
    unidades = this.unidades,
    idProducto = this.idProducto,
    idPedido = this.idPedido,
    precio = this.precio
)