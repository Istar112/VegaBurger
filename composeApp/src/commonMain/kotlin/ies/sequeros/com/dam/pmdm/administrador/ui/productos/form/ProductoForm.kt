package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Icecream

import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categoria.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriasViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductosViewModel
import ies.sequeros.com.dam.pmdm.commons.ui.ImagenDesdePath
import ies.sequeros.com.dam.pmdm.commons.ui.SelectorImagenComposable
import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.hombre


@Composable
fun ProductoForm(
    //appViewModel: AppViewModel,
    productoViewModel: ProductosViewModel,
    categoriasViewModel: CategoriasViewModel,
    onClose: () -> Unit,
    onConfirm: (datos: ProductoFormState) -> Unit = {},
    productoFormularioViewModel: ProductoFormViewModel = viewModel {
        ProductoFormViewModel(
            productoViewModel.selected.value, //onConfirm
        )
    },
    categoriaDTO: CategoriaDTO? = null
) {
    val state by productoFormularioViewModel.uiState.collectAsState()
    val formValid by productoFormularioViewModel.isFormValid.collectAsState()
    val selected = productoViewModel.selected.collectAsState()
    val categorias = categoriasViewModel.items.collectAsState()
    val imgPath =
        remember { mutableStateOf(if (state.imgPath != null && state.imgPath.isNotEmpty()) state.imgPath else "") }

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .defaultMinSize(minHeight = 200.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ){
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(scrollState), // 👈 Aquí el scroll vertical
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Fastfood,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = if (selected.value == null)
                        "Crear nuevo producto"
                    else
                        "Editar producto",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Espacio antes del botón

            // nombre
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { productoFormularioViewModel.onNombreChange(it) },
                label = { Text("Nombre") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()

            )
            state.nombreError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Descripcion
            if (selected.value == null) {
                OutlinedTextField(
                    value = state.descripcion,
                    onValueChange = { productoFormularioViewModel.onDescripcionChange(it) },
                    label = { Text("Descripción") },
                    isError = state.descripcionError != null,
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,


                )
            }
            // Precio
            OutlinedTextField(
                value = state.precio,
                onValueChange = { productoFormularioViewModel.onPrecioChange(it )},
                label = { Text("Precio") },
                leadingIcon = { Icon(Icons.Default.Euro, contentDescription = null) },
                isError = state.precioError != null,
                modifier = Modifier.fillMaxWidth()
            )

            // Combobox
            CategoriasComboBox(
                categorias = categorias.value,
                current = categoriaDTO,
                onSelect = {
                    productoFormularioViewModel.onIdCategoriaChange(it.id)
                }

            )




            // Checkbox Activo y pendiente entrega
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.activar,
                        onCheckedChange = { productoFormularioViewModel.onActivarChange(it) }
                    )
                    Text("Activo", style = MaterialTheme.typography.bodyMedium)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.pendienteEntrega,
                        onCheckedChange = { productoFormularioViewModel.onPendienteEntregaChange(it) }
                    )
                    Text("Pendiente de entrega", style = MaterialTheme.typography.bodyMedium)
                }
            }


            //  Selector de imagen producto
            Text("Selecciona una imagen:", style = MaterialTheme.typography.titleSmall)

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            val scope = rememberCoroutineScope()
            SelectorImagenComposable({ it: String ->
                productoFormularioViewModel.onImagePathChange(it)//  dependienteViewModel.almacenDatos.copy(it, "prueba","/dependientes_imgs/")
                imgPath.value = it
            })
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            ImagenDesdePath(imgPath, Res.drawable.hombre, Modifier.fillMaxSize())
            state.imgPathError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            //  Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // limpiar
                FilledTonalButton(onClick = { productoFormularioViewModel.clear() }) {
                    Icon(Icons.Default.Autorenew, contentDescription = null)
                    //Spacer(Modifier.width(6.dp))
                    //Text("Limpiar")
                }

                // guardar
                Button(
                    onClick = {
                        productoFormularioViewModel.submit(
                            onSuccess = {

                                onConfirm(it)
                            },
                            onFailure = { /* no limpiar */ }
                        )


                    },
                    enabled = formValid
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    // Spacer(Modifier.width(6.dp))
                    //Text("" + formValid.toString())
                }

                FilledTonalButton(onClick = { onClose() }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    //Spacer(Modifier.width(6.dp))
                    //Text("Cancelar")
                }
            }
        }
    }

}


