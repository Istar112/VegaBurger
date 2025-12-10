package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.BorrarPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidosCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidosUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.ListarPedidosUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.PedidosDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PedidosViewModel(
    private val pedidosRepositorio: IPedidoRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {

    private val borrarPedidoUseCase: BorrarPedidoUseCase
    private val crearPedidosUseCase: CrearPedidosUseCase
    private val listarPedidosUseCase: ListarPedidosUseCase

    private val _items = MutableStateFlow<MutableList<PedidosDTO>>(mutableListOf())
    val items: StateFlow<List<PedidosDTO>> = _items.asStateFlow()

    private val _selected = MutableStateFlow<PedidosDTO?>(null)
    val selected = _selected.asStateFlow()

    init {
        borrarPedidoUseCase = BorrarPedidoUseCase(pedidosRepositorio, almacenDatos)
        crearPedidosUseCase = CrearPedidosUseCase(pedidosRepositorio, almacenDatos)
        listarPedidosUseCase = ListarPedidosUseCase(pedidosRepositorio, almacenDatos)

        // load initial list
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        val lista = try {
            listarPedidosUseCase.invoke()
        } catch (e: Exception) {
            throw e
        }
        _items.value = lista.toMutableList()
    }

    fun setSelectedPedido(item: PedidosDTO?) {
        _selected.value = item
    }

    fun add(command: CrearPedidosCommand) {
        viewModelScope.launch {
            try {
                val created = crearPedidosUseCase.invoke(command)
                _items.value = (_items.value + created).toMutableList()
                _selected.value = created
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun delete(item: PedidosDTO) {
        viewModelScope.launch {
            try {
                val id = item.id ?: return@launch
                borrarPedidoUseCase.invoke(id)
                _items.update { current ->
                    current.filterNot { it.id == id }.toMutableList()
                }
                if (_selected.value?.id == id) {
                    _selected.value = null
                }
            } catch (e: Exception) {
                // handle or rethrow
                throw e
            }
        }
    }

    fun reload() {
        viewModelScope.launch {
            refresh()
        }
    }
}