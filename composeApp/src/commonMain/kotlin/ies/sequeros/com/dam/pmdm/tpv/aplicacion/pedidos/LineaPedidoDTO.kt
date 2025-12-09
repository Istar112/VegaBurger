package ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos

data class LineaPedidoDTO(
    var id: String,
    var unidades: Int = 1,
    var idProducto: String,
    var idPedido: String,
    var precio: Float
)
