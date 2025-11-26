package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable

@Serializable
data class Producto (
    var id: String,
    var nombre: String,
    var imgPath: String,
    var pendienteEntrega: Boolean,
    var uuidCategoria: String
)