package ies.sequeros.com.dam.pmdm.administrador.ui.categorias.from

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.from.CategoriaFromState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriaFromViewModel (private val item: CategoriaDTO?,
                                onSuccess: (CategoriaFromState) -> Unit): ViewModel() {

    private val _uiState = MutableStateFlow(CategoriaFromState(
        nombre = item?.nombre ?: "",
        activar = item?.activar?:false,
        imgPath = item?.imgPath?:"",

    ))
    val uiState: StateFlow<CategoriaFromState> = _uiState.asStateFlow()

    //para saber si el formulario es válido
    val isFormValid: StateFlow<Boolean> = uiState.map { state ->
        if(item==null)
            state.nombreError == null &&
                    state.imgPathError ==null &&
                    state.imgPathError==null &&

                    !state.nombre.isBlank() &&
                    state.imgPath.isNotBlank()

        else{
            state.nombreError == null &&
                    state.imgPathError ==null &&
                    state.imgPathError==null &&
                    !state.nombre.isBlank() &&
                    state.imgPath.isNotBlank()

        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun onNombreChange(v: String) {
        _uiState.value = _uiState.value.copy(nombre = v, nombreError = validateNombre(v))
    }

    fun onImgPathChange(v: String) {
        _uiState.value = _uiState.value.copy(imgPath =  v, imgPathError =  validateImgPath(v))
    }

    fun onEnabledChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(
            activar =  v

        )
    }

    fun clear() {
        _uiState.value = CategoriaFromState()
    }

    private fun validateNombre(nombre: String): String? {
        if (nombre.isBlank()) return "El nombre es obligatorio"
        if (nombre.length < 2) return "El nombre es muy corto"
        return null
    }
    private fun validateImgPath(path: String): String? {
        if (path.isBlank()) return "La imagen es obligatoria"
        return null
    }


    fun validateAll(): Boolean {
        val s = _uiState.value
        val nombreErr = validateNombre(s.nombre)
        val imageErr=validateImgPath(s.imgPath)
        val newState = s.copy(
            nombreError = nombreErr,
            imgPathError = imageErr,

            submitted = true
        )
        _uiState.value = newState
        return listOf(nombreErr,imageErr).all { it == null }
    }

    //se le pasan lambdas para ejecutar código en caso de éxito o error
    fun submit(
        onSuccess: ( CategoriaFromState) -> Unit,
        onFailure: ((CategoriaFromState) -> Unit)? = null
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