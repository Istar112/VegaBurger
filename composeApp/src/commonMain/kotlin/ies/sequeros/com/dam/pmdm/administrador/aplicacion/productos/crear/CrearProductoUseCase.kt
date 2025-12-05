package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearProductoUseCase(private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos) {
    suspend fun invoke(crearProductoCommand: CrearProductoCommand): ProductoDTO{
        if (repositorio.findByName(crearProductoCommand.nombre)!=null){
            throw IllegalArgumentException("El nombre ya está registrado")
        }
        val id=generateUUID()
        val imageName=almacenDatos.copy(crearProductoCommand.imgPath,id,"/Producto/")
        val item = Producto(
            id = id,
            nombre = crearProductoCommand.nombre,
            imgPath = imageName,
            pendienteEntrega = true,
            idCategoria = crearProductoCommand.idCategoria,
            descripcion = crearProductoCommand.descripcion,
            precio = crearProductoCommand.precio.toFloat(),
            activar = crearProductoCommand.activar
        )
        val element = repositorio.findByName(item.nombre)
        if(element!=null)
            throw IllegalArgumentException("El nombre ya está registrado")
        repositorio.add(item)
        return item.toDTO(almacenDatos.getAppDataDir()+"/Producto/")
    }
}
