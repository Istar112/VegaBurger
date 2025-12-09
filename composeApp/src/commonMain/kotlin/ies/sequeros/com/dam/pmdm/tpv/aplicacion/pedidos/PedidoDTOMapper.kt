package ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos

import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido


fun Pedido.toDTO(path:String="") =  PedidoDTO(
    id = id,
    idDependiente = idDependiente,
    npTotales = npTotales,
    nombreUsuario = nombreUsuario,
    importeTotal = importeTotal,
    npPendientesEntrega = npPendientesEntrega,
)

fun PedidoDTO.toPedido(): Pedido = Pedido(
    id = id,
    idDependiente = idDependiente,
    npTotales = npTotales,
    nombreUsuario = nombreUsuario,
    importeTotal = importeTotal,
    npPendientesEntrega = npPendientesEntrega,
)