package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ListarCategoriaUseCase (private val repositorio: ICategoriaRepositorio, private val almacenDatos: AlmacenDatos){

    suspend fun invoke( ): List<CategoriaDTO>{
        val items= repositorio.getAll().map { it.toDTO(if(it.imgPath.isEmpty())"" else almacenDatos.getAppDataDir()+"/categoria/") }
        return items
    }
}