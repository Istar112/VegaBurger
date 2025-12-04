package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActivarProductoUseCase (private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos){
    suspend fun invoke(command: ActivarProductoCommand): ProductoDTO {
        val item: Producto?=repositorio.getById(command.id)
        if (item==null){
            throw IllegalArgumentException("El producto no está registrado")
        }
        var newProducto=item.copy(
            activar = command.activar,
        )
        repositorio.update(newProducto)
        return newProducto.toDTO(almacenDatos.getAppDataDir()+"/Producto/")
    }
}
