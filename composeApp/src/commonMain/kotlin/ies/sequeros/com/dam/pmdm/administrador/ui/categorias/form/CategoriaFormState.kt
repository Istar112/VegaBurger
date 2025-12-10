package ies.sequeros.com.dam.pmdm.administrador.ui.categorias.form

data class CategoriaFormState (
    val id: String="",
    val nombre: String="",
    val imgPath: String="default",
    val activar: Boolean=false,
    //errores
    val nombreError: String?=null,
    val imgPathError: String?=null,
    // para controlar si se intentó enviar (mostrar errores globales)
    val submitted: Boolean = false
)