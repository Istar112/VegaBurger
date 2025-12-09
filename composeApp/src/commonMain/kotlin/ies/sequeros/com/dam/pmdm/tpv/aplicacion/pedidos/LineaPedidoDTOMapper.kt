package ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos

import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import kotlin.String

fun LineaPedido.toDTO(path:String="") = LineaPedidoDTO(
    id = id,
    unidades = unidades,
    idProducto = idProducto,
    idPedido = idPedido,
    precio = precio,

)
fun LineaPedidoDTO.toLineaPedido()= LineaPedido(
    id = id,
    unidades = unidades,
    idProducto = idProducto,
    idPedido = idPedido,
    precio = precio,
    )