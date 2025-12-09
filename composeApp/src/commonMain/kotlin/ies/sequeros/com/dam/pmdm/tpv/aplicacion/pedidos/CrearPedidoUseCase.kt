package ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos

import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.PedidoDTO

class CrearPedidoUseCase(
    private val repositorio : IPedidoRepositorio,
    private val repositorioDependientes: IDependienteRepositorio,
    private val almacenDatos: AlmacenDatos
){
    suspend fun invoke (pedido: PedidoDTO):PedidoDTO{
        val idDependiente = repositorioDependientes.getIdDependienteActivo()
        val id = generateUUID()
        val item = pedido.toPedido()
        repositorio.add(item)
        return item.toDTO(almacenDatos.getAppDataDir()+"/pedidos/")
    }

}