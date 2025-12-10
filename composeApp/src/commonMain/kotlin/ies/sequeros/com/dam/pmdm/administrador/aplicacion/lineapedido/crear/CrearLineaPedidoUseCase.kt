package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearLineaPedidoUseCase(
    private val repositorio: ILineaPedidoRepositorio,
    private val almacenDatos: AlmacenDatos
) {
    suspend operator fun invoke(command: CrearLineaPedidoCommand): LineaPedidoDTO {
        val all = repositorio.getAll()
        val existing = all.firstOrNull { it.idProducto == command.productoId && it.idPedido == command.pedidoId }

        if (existing != null) {
            val updated = existing.copy(
                unidades = existing.unidades + command.unidades,
                precio = existing.precio + command.precio
            )
            repositorio.update(updated)
            return updated.toDTO()
        }

        val entity = LineaPedido(
            id = generateUUID(),
            unidades = command.unidades,
            idProducto = command.productoId,
            idPedido = command.pedidoId,
            precio = command.precio
        )
        repositorio.add(entity)
        return entity.toDTO()
    }
}