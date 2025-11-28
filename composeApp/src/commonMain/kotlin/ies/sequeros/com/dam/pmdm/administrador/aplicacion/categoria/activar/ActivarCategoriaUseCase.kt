package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.activar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.activar.ActivarDependienteCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.listar.DependienteDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActivarCategoriaUseCase ( private val repositorio: ICategoriaRepositorio, private val almacenDatos: AlmacenDatos){
    suspend fun invoke(command: ActivarCategoriaCommand): CategoriaDTO{
        val item: Categoria?=repositorio.getById(command.id)
        if (item==null){
            throw IllegalArgumentException("La categoría no está registrada")
        }
        var newCategoria=item.copy(
            activar = command.activar,
        )
        repositorio.update(newCategoria)
        return newCategoria.toDTO(almacenDatos.getAppDataDir()+"/categoria/")
    }
}
