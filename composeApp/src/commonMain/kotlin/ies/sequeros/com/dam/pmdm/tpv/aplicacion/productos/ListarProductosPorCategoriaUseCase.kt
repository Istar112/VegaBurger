package ies.sequeros.com.dam.pmdm.tpv.aplicacion.productos

import ies.sequeros.com.dam.pmdm.tpv.aplicacion.productos.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos


class ListarProductosPorCategoriaUseCase(
    private val repositorio: IProductoRepositorio,
    private val almacenDatos: AlmacenDatos
) {
    suspend fun invoke(id: String): List<ProductoDTO> {
      val items = repositorio.getByCategoriaId(id).map { it.toDTO(if(it.imgPath.isEmpty())"" else almacenDatos.getAppDataDir()+"/Producto/") }
        return items
    }

}