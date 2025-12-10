package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar

data class PedidosDTO (
    var id: String,
    var idDependiente: String,
    var npTotales: Int,
    var nombreUsuario: String,
    var importeTotal: Float,
    var npPendientesEntrega: Int
)