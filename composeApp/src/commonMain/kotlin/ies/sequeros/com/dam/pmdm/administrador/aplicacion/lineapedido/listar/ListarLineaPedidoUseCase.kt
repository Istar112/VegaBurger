package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ListarLineaPedidoUseCase (private val repositorio: ILineaPedidoRepositorio, private val almacenDatos: AlmacenDatos){

    suspend operator fun invoke(): List<LineaPedidoDTO> {
        val items = repositorio.getAll().map { linea ->
            val path = ""
            linea.toDTO(path)
        }
        return items
    }
}