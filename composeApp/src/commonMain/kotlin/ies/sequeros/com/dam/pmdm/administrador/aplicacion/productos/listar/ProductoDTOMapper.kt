package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar


import ies.sequeros.com.dam.pmdm.Principal
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto

fun Producto.toDTO(path:String="") = ProductoDTO(
    id = id,
    nombre = nombre,
    imgPath = path + imgPath,
    pendienteEntrega = pendienteEntrega,
    idCategoria = idCategoria,
    descripcion = descripcion,
    precio = precio,
    activar = activar
)
fun ProductoDTO.toProducto() = Producto(
    id = id,
    nombre = nombre,
    imgPath=imgPath,
    pendienteEntrega = pendienteEntrega,
    idCategoria = idCategoria,
    descripcion = descripcion,
    precio = precio,
    activar = activar
)