package ies.sequeros.com.dam.pmdm.tpv.aplicacion.categorias

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria

fun Categoria.toDTO(path:String="") = CategoriaDTO(
    id = id,
    nombre = nombre,
    imgPath=path+imgPath,
    activar
)
fun Categoria.toCategoria()= Categoria(
    id = id,
    nombre = nombre,
    imgPath=imgPath,
    activar
)