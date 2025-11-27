package ies.sequeros.com.dam.pmdm.administrador.modelo

import kotlinx.serialization.Serializable

@Serializable
data class LineaPedido (
    var id: String,
    var unidades: Int,
    var idProducto: String,
    var precio: Float
)