package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear

data class CrearLineaPedidoCommand(
    val pedidoId: String,
    val productoId: String,
    val unidades: Int = 1,
    val precio: Float
)