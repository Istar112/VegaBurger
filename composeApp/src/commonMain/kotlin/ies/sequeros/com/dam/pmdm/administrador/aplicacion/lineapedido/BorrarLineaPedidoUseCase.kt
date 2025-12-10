package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class BorrarLineaPedidoUseCase(
    private val repositorio: ILineaPedidoRepositorio,
    private val almacenDatos: AlmacenDatos
) {
    suspend operator fun invoke(id: String): Boolean {
        return repositorio.remove(id)
    }
}