package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

data class ProductoFormState (
    //var id: String,
    var nombre: String = "",
    var imgPath: String = "",
    var pendienteEntrega: Boolean = false,
    var idCategoria: String = "",
    var descripcion: String = "",
    var precio: String = "",
    var activar: Boolean = false,

    // errores (null = sin error)
    val nombreError: String? = null,
    val imgPathError: String? = null,
    val pendienteEntregaError: String? = null,
    val idCategoriaError: String? = null,
    val descripcionError: String? = null,
    val precioError: String? = null,
    val activarError: String? = null,

    // para controlar si se intentó enviar (mostrar errores globales)
    val submitted: Boolean = false
)