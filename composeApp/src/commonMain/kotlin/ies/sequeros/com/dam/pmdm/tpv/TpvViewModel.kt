package ies.sequeros.com.dam.pmdm.tpv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.ListarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.CrearLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.CrearPedidoUseCase
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.productos.ListarProductosPorCategoriaUseCase
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.productos.ProductoDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TpvViewModel (
    private val repoCategorias : ICategoriaRepositorio,
    private val repoProductos : IProductoRepositorio,
    private val repoLineaPedidos: ILineaPedidoRepositorio,
    private val repoPedidos : IPedidoRepositorio,
    private val repoDependientes: IDependienteRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {

    // Casos de uso
    private val listarCategoriasUseCase = ListarCategoriaUseCase(repoCategorias, almacenDatos)
    private val listarProductosPorCategoriaUseCase = ListarProductosPorCategoriaUseCase(repoProductos, almacenDatos)
    private val crearLineaPedidoUseCase = CrearLineaPedidoUseCase(repoLineaPedidos, almacenDatos)
    private val crearPedidoUseCase = CrearPedidoUseCase(repoPedidos, repoDependientes, almacenDatos)

    // --- Categria ----//
    private val _itemsCategoria = MutableStateFlow<MutableList<CategoriaDTO>>(mutableListOf())
    val itemsCategoria: StateFlow<List<CategoriaDTO>> = _itemsCategoria.asStateFlow()

    // ----Linea Pedidos--//
    private val _itemsLineaPedidos = MutableStateFlow<List<LineaPedidoDTO>>(mutableListOf())
    val itemsLineaPedidos: StateFlow<List<LineaPedidoDTO>> = _itemsLineaPedidos
    // -- Categoria seleccionada --- //
    private val _categoriaSeleccionada = MutableStateFlow<CategoriaDTO?>(null)
    val categoriaSeleccionada = _categoriaSeleccionada.asStateFlow()
    // -- Producto seleccionado -- //
    private val _productoSeleccionado = MutableStateFlow<ProductoDTO?>(null)
    val productoSeleccionado = _productoSeleccionado.asStateFlow()
    // -- Pedido actual --- //
    private val _pedidoActual = MutableStateFlow<PedidoDTO?>(null)
    val pedidoActual = _pedidoActual.asStateFlow()

    private val _todosProductos = MutableStateFlow<List<ProductoDTO>>(emptyList())
    val todosProductos: StateFlow<List<ProductoDTO>> = _todosProductos.asStateFlow()

    // mapa global de productos usados en cualquier pedido
    private val _todosProductosMap = MutableStateFlow<Map<String, ProductoDTO>>(emptyMap())
    val todosProductosMap = _todosProductosMap.asStateFlow()

    private val _productosFiltrados = MutableStateFlow<List<ProductoDTO>>(emptyList())
    val productosFiltrados = _productosFiltrados.asStateFlow()

    init {
            cargarCategorias()
    }

    fun cargarCategorias() {
        viewModelScope.launch {
            val items = listarCategoriasUseCase.invoke()
            _itemsCategoria.value.clear()
            _itemsCategoria.value.addAll(items)
        }
    }

    fun seleccionarCategoria(categoria: CategoriaDTO) {
        _categoriaSeleccionada.value = categoria
        cargarProductosDeCategoria(categoria.id)
    }

    fun seleccionarProducto(producto: ProductoDTO) {
        _productoSeleccionado.value = producto
    }

    private fun cargarProductosDeCategoria(categoriaId: String) {
        viewModelScope.launch {

            // productos SOLO visibles en la pantalla actual
            val productos = listarProductosPorCategoriaUseCase.invoke(categoriaId)
            _productosFiltrados.value = productos

            // productos globales (se agregan si no existen)
            val listaGlobal = _todosProductos.value.toMutableList()
            productos.forEach { p ->
                if (!listaGlobal.any { it.id == p.id }) {
                    listaGlobal.add(p)
                }
            }
            _todosProductos.value = listaGlobal

            // Mapa global de acceso por ID
            _todosProductosMap.value = listaGlobal.associateBy { it.id }
        }
    }



    fun crearPedidoSiNoExiste() {
        if (_pedidoActual.value == null) {
            viewModelScope.launch {
                _pedidoActual.value = PedidoDTO(
                    id = generateUUID(),
                    idDependiente = repoDependientes.getIdDependienteActivo(),
                    npTotales = 0,
                    nombreUsuario = "",
                    importeTotal = 0f,
                    npPendientesEntrega = 0
                )

            }
        }
    }

    // Añadir producto en memoria
    fun añadirProductoLineaPedido(producto: ProductoDTO) {
        viewModelScope.launch {
            // Crear pedido temporal si no existe
            crearPedidoSiNoExiste()

            val listaActual = _itemsLineaPedidos.value.toMutableList()
            val indexExistente = listaActual.indexOfFirst { it.idProducto == producto.id }

            if (indexExistente != -1) {
                val existente = listaActual[indexExistente]
                listaActual[indexExistente] = existente.copy(
                    unidades = existente.unidades + 1,
                    precio = existente.precio + producto.precio
                )
            } else {
                listaActual.add(
                    LineaPedidoDTO(
                        id = generateUUID(),
                        idProducto = producto.id,
                        idPedido = _pedidoActual.value!!.id, // ahora seguro que no es null
                        unidades = 1,
                        precio = producto.precio
                    )
                )
            }

            _itemsLineaPedidos.value = listaActual
            // Actualizar mapa global de productos
            val mapaActual = _todosProductosMap.value.toMutableMap()
            mapaActual[producto.id] = producto
            _todosProductosMap.value = mapaActual

            calcularImporteTotal()
            calcularTotalProductos()
        }
    }

    fun eliminarProductoLineaPedido(producto: ProductoDTO) {
        viewModelScope.launch {
            // Obtenemos la lista actual de forma segura para modificarla
            val listaActual = _itemsLineaPedidos.value.toMutableList()


            val indexExistente = listaActual.indexOfFirst { it.idProducto == producto.id }

            if (indexExistente != -1) {
                val existente = listaActual[indexExistente]

                if (existente.unidades > 1) {
                    // Hay más de 1 unidad: Disminuir la cantidad en 1
                    listaActual[indexExistente] = existente.copy(
                        unidades = existente.unidades - 1,
                        precio = existente.precio - producto.precio
                    )
                } else {
                    // Solo queda 1 unidad: Eliminar la línea de pedido por completo
                    listaActual.removeAt(indexExistente)
                }

                // Actualizamos el estado (la lista de líneas de pedido)
                _itemsLineaPedidos.value = listaActual

                // Recalculamos el importe total del pedido (esto actualiza la UI)
                calcularImporteTotal()
                calcularTotalProductos()
            }
        }
    }
    fun calcularImporteTotal() {
        val items = _itemsLineaPedidos.value ?: emptyList()

        var total = 0.0f
        for (item in items) {
            total += item.precio
        }

        _pedidoActual.value = _pedidoActual.value?.copy(importeTotal = total)
    }

    fun añadirNombrePedido(nombre: String) {
        _pedidoActual.value = _pedidoActual.value?.copy(nombreUsuario = nombre)
    }
    fun calcularTotalProductos() {
        val items = _itemsLineaPedidos.value ?: emptyList()
        val totalProductos = items.sumOf { it.unidades }
        _pedidoActual.value = _pedidoActual.value?.copy(npTotales = totalProductos)
    }





    // Confirmar pedido: insertar pedido y todas las líneas
    fun confirmarPedido() {
        viewModelScope.launch {
            val pedido = _pedidoActual.value ?: return@launch

            // Insertar pedido en BBDD
            val pedidoInsertado = crearPedidoUseCase.invoke(pedido)

            // Guardar todas las líneas de pedido con el id del pedido guardado
            _itemsLineaPedidos.value.forEach { linea ->
                // Buscar el producto original por id
                val producto = todosProductosMap.value[linea.idProducto]
                if (producto != null) {
                    crearLineaPedidoUseCase.invoke(
                        producto = producto,
                        idPedido = pedidoInsertado.id
                    )
                }
            }


            // Limpiar pedido y líneas en memoria
            _pedidoActual.value = null
            _itemsLineaPedidos.value = emptyList()
        }
    }
}








