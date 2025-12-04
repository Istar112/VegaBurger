package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear

data class CrearProductoCommand (
    //var id: String,
    var nombre: String,
    var imgPath: String,
    var pendienteEntrega: Boolean,
    var idCategoria: String,
    var descripcion: String,
    var precio: Float,
    var activar: Boolean
)