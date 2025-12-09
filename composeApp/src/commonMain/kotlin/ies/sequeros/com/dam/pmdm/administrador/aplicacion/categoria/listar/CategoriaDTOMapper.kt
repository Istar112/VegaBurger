package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import kotlin.Boolean

fun Categoria.toDTO(path:String="") = CategoriaDTO(
    id = id,
    nombre = nombre,
    imgPath=path+imgPath,
    activar
)
fun CategoriaDTO.toCategoria()= Categoria(
    id = id,
    nombre = nombre,
    imgPath=imgPath,
    activar
)