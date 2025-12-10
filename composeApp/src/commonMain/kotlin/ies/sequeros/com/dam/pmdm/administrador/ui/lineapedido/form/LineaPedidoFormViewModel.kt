package ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido.LineaPedidoViewModel
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LineaPedidoFormViewModel(
    private val lineasViewModel: LineaPedidoViewModel,
    private val crearUseCase: CrearLineaPedidoUseCase,
    private val almacenDatos: AlmacenDatos
) : ViewModel() {

    private val _uiState = MutableStateFlow(LineaPedidoFormState())
    val uiState: StateFlow<LineaPedidoFormState> = _uiState.asStateFlow()

    fun load(initial: LineaPedidoDTO?) {
        _uiState.value = LineaPedidoFormState(
            id = initial?.id ?: "",
            pedidoId = initial?.idPedido ?: "",
            productoId = initial?.idProducto ?: "",
            unidades = initial?.unidades ?: 1,
            precio = initial?.precio ?: 0f,
            submitted = false,
            isLoading = false,
            error = null
        )
    }

    fun onProductoIdChange(value: String) {
        _uiState.value = _uiState.value.copy(productoId = value, error = null)
    }

    fun onUnidadesChange(value: Int) {
        _uiState.value = _uiState.value.copy(unidades = value, error = null)
    }

    fun onPrecioChange(value: Float) {
        _uiState.value = _uiState.value.copy(precio = value, error = null)
    }

    fun submit(onSuccess: (() -> Unit)? = null, onError: ((String) -> Unit)? = null) {
        val state = _uiState.value
        if (state.pedidoId.isBlank()) {
            val msg = "PedidoId es requerido"
            _uiState.value = state.copy(error = msg)
            onError?.invoke(msg)
            return
        }
        if (state.productoId.isBlank()) {
            val msg = "ProductoId es requerido"
            _uiState.value = state.copy(error = msg)
            onError?.invoke(msg)
            return
        }

        _uiState.value = state.copy(isLoading = true, submitted = false, error = null)

        val command = CrearLineaPedidoCommand(
            pedidoId = state.pedidoId,
            productoId = state.productoId,
            unidades = state.unidades,
            precio = state.precio
        )

        viewModelScope.launch {
            try {
                val createdDto = crearUseCase.invoke(command)
                lineasViewModel.add(command)
                _uiState.value = _uiState.value.copy(isLoading = false, submitted = true)
                onSuccess?.invoke()
            } catch (e: Exception) {
                val msg = e.message ?: "Error al guardar línea"
                _uiState.value = _uiState.value.copy(isLoading = false, error = msg)
                onError?.invoke(msg)
            }
        }
    }

    fun cancel() {
        _uiState.value = _uiState.value.copy(submitted = false, error = null)
    }
}