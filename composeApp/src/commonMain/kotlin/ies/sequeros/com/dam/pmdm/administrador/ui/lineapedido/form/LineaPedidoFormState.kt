package ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido.form

data class LineaPedidoFormState(
    val id: String = "",
    val pedidoId: String = "",
    val productoId: String = "",
    val unidades: Int = 1,
    val precio: Float = 0f,
    val submitted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)