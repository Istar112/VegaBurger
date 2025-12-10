package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear

data class CrearPedidosCommand (
    var id: String,
    var idDependiente: String,
    var npTotales: Int,
    var nombreUsuario: String,
    var importeTotal: Float,
    var npPendientesEntrega: Int
)