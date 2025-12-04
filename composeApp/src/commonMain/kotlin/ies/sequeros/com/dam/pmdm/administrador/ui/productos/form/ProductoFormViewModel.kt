package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoFormViewModel(
    private val item: ProductoDTO?,
    //onSuccess: (ProductoFormState) -> Unit
) : ViewModel() {
    // State flow para el formulario
    private val _uiState = MutableStateFlow(
        ProductoFormState(
            nombre = item?.nombre ?: "",
            imgPath = item?.imgPath ?: "",
            pendienteEntrega = item?.pendienteEntrega ?: true,
            idCategoria = item?.idCategoria ?: "",
            descripcion = item?.descripcion ?: "",
            precio = item?.precio ?: 0f,
            activar = item?.activar ?: false,
        )
    )

    // Exponemos el state flow como un state flow el que se va a pasar
    val uiState: StateFlow<ProductoFormState> = _uiState.asStateFlow()

    // Para saber si el formulario es valido

    val isFormValid: StateFlow<Boolean> = uiState.map { state ->
        if (item == null)
            // Validación para el nuevo producto
            state.nombreError == null &&
                    //state.imgPathError == null &&
                    state.pendienteEntregaError == null &&
                    //state.idCategoriaError == null &&
                    state.descripcionError == null &&
                    state.precioError == null &&
                    state.activarError == null &&

                    !state.nombre.isBlank() &&
                    //!state.imgPath.isBlank() &&
                    //!state.idCategoria.isBlank() &&
                    !state.descripcion.isBlank() &&
                    state.precio > 0
        else {
            // validación para edidicón
            state.nombreError == null &&
                    state.descripcionError == null &&
                    state.pendienteEntregaError == null &&
                    //state.idCategoriaError == null &&
                    state.precioError == null &&

                    !state.nombre.isBlank() &&
                    !state.descripcion.isBlank()
                    //!state.idCategoria.isBlank()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = true
    )

    fun onNombreChange(v: String) {
        _uiState.value = _uiState.value.copy(nombre = v, nombreError = validateNombre(v))
    }

    fun onDescripcionChange(v: String) {
        _uiState.value =
            _uiState.value.copy(descripcion = v, descripcionError = validateDescripcion(v))
    }

    fun onPendienteEntregaChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(
            pendienteEntrega = v,
            pendienteEntregaError = validatePendienteEntrega()
        )
    }

    fun onIdCategoriaChange(v: String) {
        _uiState.value =
            _uiState.value.copy(idCategoria = v, idCategoriaError = validateIdCategoria(v))
    }

    fun onPrecioChange(v: Float) {
        _uiState.value = _uiState.value.copy(precio = v, precioError = validatePrecio())
    }

    fun onImagePathChange(v: String) {
        _uiState.value = _uiState.value.copy(imgPath = v, imgPathError = validateImagePath(v))

    }

    fun onActivarChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(activar = v)
    }

    fun clear() {
        _uiState.value = ProductoFormState()
    }

    // Validaciones individuales
    private fun validateNombre(nombre: String): String? {
        if (nombre.isBlank()) return "El nombre es obligatorio"
        return null
    }

    // validamos imagen? obligatorio?
    private fun validateImagePath(path: String): String? {
        //if (path.isBlank()) return "La imagen es obligatoria"
        return null
    }

    private fun validateDescripcion(descripcion: String): String? {
        if (descripcion.isBlank()) return "La descripcion es obligatoria"
        return null
    }

    // no necesita validacion lo podemos quitar
    private fun validatePendienteEntrega(): String? {
        return null
    }
    // ya veremos que hacemos con el id
    private fun validateIdCategoria(idCategoria: String): String? {
       // if (idCategoria.isBlank()) return "La categoria es obligatoria"
        return null
    }

    private fun validatePrecio(): String? {
        if (_uiState.value.precio <= 0) return "El precio debe ser mayor que 0"
        return null
    }

    // tampoco seria necesario
    private fun validateActivar(): String? {
        return null
    }

    // ---------------------//

    fun validateAll(): Boolean {
        val s = _uiState.value

        // validaciones
        val nombreErr = validateNombre(s.nombre)
        val imageErr=validateImagePath(s.imgPath)
        val descripcionErr = validateDescripcion(s.descripcion)
        val pendienteEntregaErr = validatePendienteEntrega()
        val idCategoriaErr = validateIdCategoria(s.idCategoria)
        val precioErr = validatePrecio()
        val activarErr = validateActivar()

        // se crea una copia con los errores actualizados
        val newState = s.copy(
            nombreError = nombreErr,
            imgPathError = imageErr,
            descripcionError = descripcionErr,
            pendienteEntregaError = pendienteEntregaErr,
            idCategoriaError = idCategoriaErr,
            precioError = precioErr,
            activarError = activarErr,

            // Marcar como enviado para mostar errores
            submitted = true
        )
        _uiState.value = newState
        // si hay errores, no se puede enviar, tienen que ser nulas
        return listOf(nombreErr, imageErr, descripcionErr, pendienteEntregaErr, idCategoriaErr, precioErr, activarErr).all { it == null }
    }

    // --------------------- Guardado del formulario ---------//

    fun submit(
        onSuccess: (ProductoFormState) -> Unit,
        onFailure: ((ProductoFormState) -> Unit)? = null
    ) {

        //se ejecuta en una corrutina, evitando que se bloque la interfaz gráficas
        viewModelScope.launch {
            val ok = validateAll()
            if (ok) {
                onSuccess(_uiState.value)
            } else {
                onFailure?.invoke(_uiState.value)
            }
        }
    }


}


