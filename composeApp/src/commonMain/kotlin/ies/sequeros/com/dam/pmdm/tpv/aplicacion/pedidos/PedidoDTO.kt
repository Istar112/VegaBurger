package ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos

data class PedidoDTO (
    var id: String,
    var idDependiente: String,
    var npTotales: Int,
    var nombreUsuario: String,
    var importeTotal: Float,
    var npPendientesEntrega: Int
)