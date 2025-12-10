package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.PedidosDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class PedidoFormViewModel(
    private val item: PedidosDTO?,
    private val onSuccess: (PedidoFromState) -> Unit
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PedidoFromState(
            idDependiente = item?.idDependiente ?: "",
            nombreUsuario = item?.nombreUsuario ?: "",
            npTotales = item?.npTotales ?: 0,
            npPendientesEntrega = item?.npPendientesEntrega ?: 0,
            importeTotal = item?.importeTotal ?: 0f
        )
    )
    val uiState: StateFlow<PedidoFromState> = _uiState.asStateFlow()

    // Valid flag (solo ejemplo, puede usarse para habilitar botón)
    val isFormValid = uiState.map { s ->
        s.idDependienteError == null &&
                s.nombreUsuarioError == null &&
                s.npTotalesError == null &&
                s.npPendientesEntregaError == null &&
                s.importeTotalError == null &&
                s.idDependiente.isNotBlank() &&
                s.nombreUsuario.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = false)

    fun onIdDependienteChange(v: String) {
        _uiState.value = _uiState.value.copy(
            idDependiente = v,
            idDependienteError = validateIdDependiente(v)
        )
    }

    fun onNombreUsuarioChange(v: String) {
        _uiState.value = _uiState.value.copy(
            nombreUsuario = v,
            nombreUsuarioError = validateNombreUsuario(v)
        )
    }

    fun onNpTotalesInput(v: String) {
        val n = v.toIntOrNull()
        _uiState.value = if (n == null && v.isNotBlank()) {
            _uiState.value.copy(npTotalesError = "npTotales debe ser entero")
        } else {
            _uiState.value.copy(npTotales = n ?: 0, npTotalesError = null)
        }
    }

    fun onNpPendientesInput(v: String) {
        val n = v.toIntOrNull()
        _uiState.value = if (n == null && v.isNotBlank()) {
            _uiState.value.copy(npPendientesEntregaError = "npPendientes debe ser entero")
        } else {
            _uiState.value.copy(npPendientesEntrega = n ?: 0, npPendientesEntregaError = null)
        }
    }

    fun onImporteTotalInput(v: String) {
        val f = v.toFloatOrNull()
        _uiState.value = if (f == null && v.isNotBlank()) {
            _uiState.value.copy(importeTotalError = "Importe no válido")
        } else {
            _uiState.value.copy(importeTotal = f ?: 0f, importeTotalError = null)
        }
    }

    fun clear() {
        _uiState.value = PedidoFromState()
    }

    private fun validateIdDependiente(v: String): String? {
        if (v.isBlank()) return "Id dependiente requerido"
        return null
    }

    private fun validateNombreUsuario(v: String): String? {
        if (v.isBlank()) return "Nombre requerido"
        if (v.length < 2) return "Nombre demasiado corto"
        return null
    }

    private fun validateNpTotalesValue(n: Int): String? {
        if (n < 0) return "npTotales no puede ser negativo"
        return null
    }

    private fun validateNpPendientesValue(n: Int): String? {
        if (n < 0) return "npPendientes no puede ser negativo"
        return null
    }

    private fun validateImporteValue(f: Float): String? {
        if (f < 0f) return "Importe no puede ser negativo"
        return null
    }

    fun validateAll(): Boolean {
        val s = _uiState.value
        val idErr = validateIdDependiente(s.idDependiente)
        val nombreErr = validateNombreUsuario(s.nombreUsuario)
        val npTotErr = validateNpTotalesValue(s.npTotales)
        val npPendErr = validateNpPendientesValue(s.npPendientesEntrega)
        val impErr = validateImporteValue(s.importeTotal)

        val newState = s.copy(
            idDependienteError = idErr,
            nombreUsuarioError = nombreErr,
            npTotalesError = npTotErr,
            npPendientesEntregaError = npPendErr,
            importeTotalError = impErr,
            submitted = true
        )
        _uiState.value = newState
        return listOf(idErr, nombreErr, npTotErr, npPendErr, impErr).all { it == null }
    }

    fun submit() {
        viewModelScope.launch {
            if (!validateAll()) return@launch
            val s = _uiState.value
            _uiState.value = s.copy(isLoading = true, error = null)
            try {
                val domain = PedidoFromState(
                    id = item?.id ?: "",
                    idDependiente = s.idDependiente,
                    npTotales = s.npTotales,
                    nombreUsuario = s.nombreUsuario,
                    importeTotal = s.importeTotal,
                    npPendientesEntrega = s.npPendientesEntrega
                )
                onSuccess(domain)
                _uiState.value = _uiState.value.copy(isLoading = false, success = true)
            } catch (t: Throwable) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = t.message ?: "Error")
            }
        }
    }
}