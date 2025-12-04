package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.combobox

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(
    label: String,
    opciones: List<String>,
    valorSeleccionado: String,
    onValorCambiado: (String) -> Unit,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true,

) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { if (habilitado) expandido = !expandido },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = valorSeleccionado,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                TrailingIcon(expanded = expandido)
            },
            enabled = habilitado,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            leadingIcon = {
                Icon(
                    Icons.Default.Icecream,
                    contentDescription = null
                )
            },

        )

        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onValorCambiado(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}

//// Luego lo usas así (más limpio):
//MiComboBoxPersonalizado(
//label = "País",
//opciones = paises,
//valorSeleccionado = paisSeleccionado,
//onValorCambiado = { paisSeleccionado = it },
//modifier = Modifier.fillMaxWidth()
//)