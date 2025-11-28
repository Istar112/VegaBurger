package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.actualizar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActualizarCategoriaUseCase(private val repositorio: ICategoriaRepositorio, private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(command: ActualizarCategoriaCommand,): CategoriaDTO {

        val item: Categoria?=repositorio.getById(command.id)
        var nuevaImagePath: String? = null
        if (item == null) {
            throw IllegalArgumentException("La categoria no está registrada")
        }
        var itemDTO: CategoriaDTO = item.toDTO(almacenDatos.getAppDataDir() + "/categoria/")
        if (itemDTO.imgPath != command.imgPath) {
            almacenDatos.remove(itemDTO.imgPath)
            nuevaImagePath = almacenDatos.copy(command.imgPath, command.id, "/categoria/")
        } else {
            nuevaImagePath = item.imgPath
        }
        var newCategoria=item.copy(
            nombre=command.nombre,
            activar = command.activar,
            imgPath = nuevaImagePath

        )
        repositorio.update(newCategoria)
        return newCategoria.toDTO(almacenDatos.getAppDataDir()+"/categoria/")
    }
}
