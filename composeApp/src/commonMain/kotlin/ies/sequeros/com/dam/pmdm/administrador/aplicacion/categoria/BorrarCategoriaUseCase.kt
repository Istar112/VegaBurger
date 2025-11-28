package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class BorrarCategoriaUseCase (private val repositorio: ICategoriaRepositorio, private val almacenDatos: AlmacenDatos){
    suspend fun invoque(id: String){
        val tempo=repositorio.getById(id)
        val elementos =repositorio.getAll();
        if(tempo==null) {
            throw IllegalArgumentException("Id no registrado.")
        }
        val tempoDto= tempo.toDTO(almacenDatos.getAppDataDir()+"/categoria/")
        repositorio.remove(id)
        almacenDatos.remove(tempoDto.imgPath)
    }
}
