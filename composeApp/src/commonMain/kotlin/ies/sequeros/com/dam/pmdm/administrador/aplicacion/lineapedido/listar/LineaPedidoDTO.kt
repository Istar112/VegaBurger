package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar

data class LineaPedidoDTO(
    val id: String,
    val unidades: Int = 1,
    val idProducto: String,
    val idPedido: String,
    val precio: Float
)