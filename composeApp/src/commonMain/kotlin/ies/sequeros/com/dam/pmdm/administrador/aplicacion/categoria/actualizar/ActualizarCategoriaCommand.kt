package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.actualizar

data class ActualizarCategoriaCommand (
    var id: String,
    var nombre: String,
    var imgPath: String,
    var activar: Boolean
)