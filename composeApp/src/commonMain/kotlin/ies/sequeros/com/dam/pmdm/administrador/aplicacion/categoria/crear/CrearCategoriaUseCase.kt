package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearCategoriaUseCase(private val repositorio: ICategoriaRepositorio, private val almacenDatos: AlmacenDatos) {
    suspend fun invoke(crearCategoriaCommand: CrearCategoriaCommand): CategoriaDTO{
        if (repositorio.findByName(crearCategoriaCommand.nombre)!=null){
            throw IllegalArgumentException("El nombre ya está registrado")
        }
        val id=generateUUID()
        val imageName=almacenDatos.copy(crearCategoriaCommand.imgPath,id,"/categoria/")
        val item = Categoria(
            id = id,
            nombre = crearCategoriaCommand.nombre,
            imgPath = imageName,
            activar = crearCategoriaCommand.activar
        )
        val element = repositorio.findByName(item.nombre)
        if(element!=null)
            throw IllegalArgumentException("El nombre ya está registrado")
        repositorio.add(item)
        return item.toDTO(almacenDatos.getAppDataDir()+"/categoria/")
    }
}
