package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.form

data class PedidoFromState(
    val id: String = "",
    val idDependiente: String = "",
    val npTotales: Int = 0,
    val nombreUsuario: String = "",
    val importeTotal: Float = 0.0f,
    val npPendientesEntrega: Int = 0,
    //errores
    val idDependienteError: String? = null,
    val nombreUsuarioError: String? = null,
    val npTotalesError: String? = null,
    val npPendientesEntregaError: String? = null,
    val importeTotalError: String? = null,

    val submitted: Boolean = false,
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)