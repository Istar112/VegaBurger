package ies.sequeros.com.dam.pmdm.administrador.ui.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.BorrarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ListarProductosUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ProductoFormState
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductosViewModel (
    private val productoRepositorio: IProductoRepositorio,
    private val almacenDatos: AlmacenDatos
): ViewModel(){
    // Casos de uso
    private val borrarProductoUseCase: BorrarProductoUseCase
    private val crearProductoUseCase: CrearProductoUseCase
    private val listarProductosUseCase: ListarProductosUseCase
    private val actualizarProductoUseCase: ActualizarProductoUseCase
    private val activarProductoUseCase: ActivarProductoUseCase

    // Flow
    private val _items = MutableStateFlow<MutableList<ProductoDTO>>(mutableListOf())
    val items : StateFlow<List<ProductoDTO>> = _items.asStateFlow()

    // Selected
    private val _selected = MutableStateFlow<ProductoDTO?>(null)
    val selected = _selected.asStateFlow()

    //init
    init {
        borrarProductoUseCase = BorrarProductoUseCase(productoRepositorio, almacenDatos)
        crearProductoUseCase = CrearProductoUseCase(productoRepositorio, almacenDatos)
        listarProductosUseCase = ListarProductosUseCase(productoRepositorio, almacenDatos)
        actualizarProductoUseCase = ActualizarProductoUseCase(productoRepositorio, almacenDatos)
        activarProductoUseCase = ActivarProductoUseCase(productoRepositorio, almacenDatos)

        viewModelScope.launch {
            var items = listarProductosUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)
        }


    }

    fun setSelectedProducto(item: ProductoDTO?) {
        _selected.value = item
    }

    fun switchEnableProducto(item: ProductoDTO) {
        val command = ActivarProductoCommand(
            item.id,
            item.activar,
        )

        viewModelScope.launch {
            val item = activarProductoUseCase.invoke(command)
            _items.value = _items.value.map {
                if (item.id == it.id)
                    item
                else
                    it
            } as MutableList<ProductoDTO>
            }
        }

    // Delete
    fun delete(item: ProductoDTO) {
        viewModelScope.launch {
            borrarProductoUseCase.invoke(item.id)
            _items.update { current ->
                current.filterNot { it.id == item.id }.toMutableList()
            }
        }
    }

    // add
    fun add(formState: ProductoFormState) {
            val command = CrearProductoCommand(
                formState.nombre,
                formState.imgPath,
                formState.pendienteEntrega,
                formState.idCategoria,
                formState.descripcion,
                formState.precio,
                formState.activar
            )
            viewModelScope.launch {
                try {
                    val user = crearProductoUseCase.invoke(command)
                    _items.value = (_items.value + user) as MutableList<ProductoDTO>
                } catch (e: Exception) {
                    throw e

                }
            }
    }

    //update
    fun update(formState: ProductoFormState) {
        val command = ActualizarProductoCommand(
            selected.value!!.id!!,
            formState.nombre,
            formState.imgPath,
            formState.pendienteEntrega,
            formState.idCategoria,
            formState.descripcion,
            formState.precio,
            formState.activar
        )
        viewModelScope.launch {
            val item = actualizarProductoUseCase.invoke(command)
            _items.update { current ->
                current.map { if (it.id == item.id) item else it } as MutableList<ProductoDTO>
            }

        }

    }

    fun save(item: ProductoFormState) {
        if (_selected.value == null)
            this.add(item)
        else
            this.update(item)
    }

}