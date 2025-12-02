package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.crear

data class CrearCategoriaCommand (
    var id: String,
    var nombre: String,
    var imgPath: String,
    var activar: Boolean
)