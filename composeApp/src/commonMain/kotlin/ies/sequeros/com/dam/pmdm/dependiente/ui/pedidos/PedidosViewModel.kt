package ies.sequeros.com.dam.pmdm.dependiente.ui.pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.dependiente.aplicacion.pedido.Listar.ListarPedidosUseCase
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.PedidoDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PedidosViewModel(
    repositorioPedidos: IPedidoRepositorio
) : ViewModel() {
    private val listarpedidosUseCase = ListarPedidosUseCase(repositorioPedidos)
    private val _pedidos = MutableStateFlow<List<PedidoDTO>>(emptyList())
    val pedidos: StateFlow<List<PedidoDTO>> = _pedidos

    init {
        cargarPedidos()
    }

    fun cargarPedidos() {
        viewModelScope.launch {
            _pedidos.value = listarpedidosUseCase.invoke()
        }
    }


}