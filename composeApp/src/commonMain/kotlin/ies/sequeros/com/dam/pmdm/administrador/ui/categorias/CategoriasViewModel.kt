package ies.sequeros.com.dam.pmdm.administrador.ui.categorias

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.BorrarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.activar.ActivarCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.activar.ActivarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.actualizar.ActualizarCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.actualizar.ActualizarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.crear.CrearCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.crear.CrearCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.ListarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.activar.ActivarDependienteCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.actualizar.ActualizarDependienteCommand


import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.cambiarpermisos.CambiarPermisosCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.cambiarpermisos.CambiarPermisosUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.crear.CrearDependienteCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.listar.DependienteDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO

import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.from.CategoriaFromState
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.form.DependienteFormState
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductosViewModel


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriasViewModel(
    //private val administradorViewModel: MainAdministradorViewModel,
    private val categoriaRepositorio: ICategoriaRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {
    //los casos de uso se crean dentro para la recomposición
    //se pueden injectar también, se tratará en próximos temas
    private val borrarCategoriaUseCase: BorrarCategoriaUseCase
    private val crearCategoriaUseCase: CrearCategoriaUseCase
    private val listarCategoriasUseCase: ListarCategoriaUseCase

    private val actualizarCategoriaUseCase: ActualizarCategoriaUseCase
    private val activarCategoriaUseCase: ActivarCategoriaUseCase

    private val _items = MutableStateFlow<MutableList<CategoriaDTO>>(mutableListOf())
    val items: StateFlow<List<CategoriaDTO>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<CategoriaDTO?>(null)
    val selected = _selected.asStateFlow()

    init {
        actualizarCategoriaUseCase = ActualizarCategoriaUseCase(categoriaRepositorio,almacenDatos)
        borrarCategoriaUseCase = BorrarCategoriaUseCase(categoriaRepositorio,almacenDatos)
        crearCategoriaUseCase = CrearCategoriaUseCase(categoriaRepositorio,almacenDatos)
        listarCategoriasUseCase = ListarCategoriaUseCase(categoriaRepositorio,almacenDatos)
        activarCategoriaUseCase = ActivarCategoriaUseCase(categoriaRepositorio,almacenDatos)
        viewModelScope.launch {
            var items = listarCategoriasUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)

        }
    }

    fun setSelectedCategoria(item: CategoriaDTO?) {
        _selected.value = item
    }

    fun switchEnableCategoria(item: CategoriaDTO) {
        val command= ActivarCategoriaCommand(
            item.id,
            item.activar,
        )

        viewModelScope.launch {
            val item=activarCategoriaUseCase.invoke(command)
            _items.value = _items.value.map {
                if (item.id == it.id)
                    item
                else
                    it
            } as MutableList<CategoriaDTO>
        }

    }


    fun delete(item: CategoriaDTO) {
        viewModelScope.launch {
            borrarCategoriaUseCase.invoke(item.id)
            _items.update { current ->
                current.filterNot { it.id == item.id }.toMutableList()
            }
        }

    }

    fun add(formState: CategoriaFromState) {
        val command = CrearCategoriaCommand(
            formState.id,
            formState.nombre,
            formState.imgPath,
            formState.activar,
        )
        viewModelScope.launch {
            try {
                val user = crearCategoriaUseCase.invoke(command)
                _items.value = (_items.value + user) as MutableList<CategoriaDTO>
            }catch (e:Exception){
                throw  e
            }

        }
    }

    fun update(formState: CategoriaFromState) {
        val command = ActualizarCategoriaCommand(
            selected.value!!.id!!,
            formState.nombre,
            formState.imgPath,
            formState.activar,

        )
        viewModelScope.launch {
            val item = actualizarCategoriaUseCase.invoke(command)
            _items.update { current ->
                current.map { if (it.id == item.id) item else it } as MutableList<CategoriaDTO>
            }
        }


    }

    fun save(item: CategoriaFromState) {
        if (_selected.value == null)
            this.add(item)
        else
            this.update(item)
    }

}