package ies.sequeros.com.dam.pmdm.administrador.modelo

import kotlinx.serialization.Serializable

@Serializable
data class Pedido (
    var id: String,
    var idDependiente: String,
    var npTotales: Int,
    var nombreUsuario: String,
    var importeTotal: Float,
    var npPendientesEntrega: Int
)

