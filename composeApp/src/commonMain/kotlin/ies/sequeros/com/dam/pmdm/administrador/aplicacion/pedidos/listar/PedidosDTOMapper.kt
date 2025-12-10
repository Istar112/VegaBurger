package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido

fun Pedido.toDTO(path: String="") = PedidosDTO(
    id = id,
    idDependiente = idDependiente,
    npTotales = npTotales,
    nombreUsuario = nombreUsuario,
    importeTotal = importeTotal,
    npPendientesEntrega = npPendientesEntrega
)
fun Pedido.toPedido()= Pedido(
    id = id,
    idDependiente = idDependiente,
    npTotales = npTotales,
    nombreUsuario = nombreUsuario,
    importeTotal = importeTotal,
    npPendientesEntrega = npPendientesEntrega
)