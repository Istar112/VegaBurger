package ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.BorrarLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.ListarLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LineaPedidoViewModel(
    private val repositorio: ILineaPedidoRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {

    private val listarUseCase = ListarLineaPedidoUseCase(repositorio, almacenDatos)
    private val crearUseCase = CrearLineaPedidoUseCase(repositorio, almacenDatos)
    private val borrarUseCase = BorrarLineaPedidoUseCase(repositorio, almacenDatos)

    private val _items = MutableStateFlow<MutableList<LineaPedidoDTO>>(mutableListOf())
    val items: StateFlow<List<LineaPedidoDTO>> = _items.asStateFlow()

    private val _selected = MutableStateFlow<LineaPedidoDTO?>(null)
    val selected = _selected.asStateFlow()

    fun loadForPedido(pedidoId: String) {
        viewModelScope.launch {
            val lista = listarUseCase.invoke()
            _items.value = lista.filter { it.idPedido == pedidoId }.toMutableList()
        }
    }

    fun add(command: CrearLineaPedidoCommand) {
        viewModelScope.launch {
            val created = crearUseCase.invoke(command)
            _items.value = (_items.value + created).toMutableList()
            _selected.value = created
        }
    }

    fun delete(item: LineaPedidoDTO) {
        viewModelScope.launch {
            val id = item.id
            borrarUseCase.invoke(id)
            _items.update { current -> current.filterNot { it.id == id }.toMutableList() }
            if (_selected.value?.id == id) _selected.value = null
        }
    }

    fun setSelected(item: LineaPedidoDTO?) {
        _selected.value = item
    }

    fun reload(pedidoId: String) {
        viewModelScope.launch {
            val lista = listarUseCase.invoke()
            _items.value = lista.filter { it.idPedido == pedidoId }.toMutableList()
        }
    }
}