package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActualizarProductoUseCase(private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(command: ActualizarProductoCommand,): ProductoDTO {

        val item: Producto?=repositorio.getById(command.id)
        var nuevaImagePath: String? = null
        if (item == null) {
            throw IllegalArgumentException("El Producto no está registrado")
        }
        var itemDTO: ProductoDTO = item.toDTO(almacenDatos.getAppDataDir() + "/Producto/")
        if (itemDTO.imgPath != command.imgPath) {
            almacenDatos.remove(itemDTO.imgPath)
            nuevaImagePath = almacenDatos.copy(command.imgPath, command.id, "/Producto/")
        } else {
            nuevaImagePath = item.imgPath
        }
        var newProducto=item.copy(
            nombre=command.nombre,
            activar = command.activar,
            imgPath = nuevaImagePath

        )
        repositorio.update(newProducto)
        return newProducto.toDTO(almacenDatos.getAppDataDir()+"/Producto/")
    }
}
