package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar

data class ActualizarProductoCommand (
    var id: String,
    var nombre: String,
    var imgPath: String,
    var pendienteEntrega: Boolean,
    var idCategoria: String,
    var descripcion: String,
    var precio: String,
    var activar: Boolean
)