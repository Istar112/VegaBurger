package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidosCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.PedidosDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearPedidosUseCase(private val repositorio: IPedidoRepositorio, private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(crearPedidosCommand: CrearPedidosCommand): PedidosDTO {
        val id=generateUUID()
        val item = Pedido(
            id = id,
            idDependiente = crearPedidosCommand.idDependiente,
            npTotales = crearPedidosCommand.npTotales,
            nombreUsuario = crearPedidosCommand.nombreUsuario,
            importeTotal = crearPedidosCommand.importeTotal,
            npPendientesEntrega = crearPedidosCommand.npPendientesEntrega
        )
        val element = repositorio.findByName(item.id)
        if(element!=null)
            throw IllegalArgumentException("El id ya está registrado")
        repositorio.add(item)
        return item.toDTO(almacenDatos.getAppDataDir()+"/pedido/")
    }
}