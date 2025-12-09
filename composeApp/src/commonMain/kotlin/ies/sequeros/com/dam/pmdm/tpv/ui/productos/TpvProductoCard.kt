package ies.sequeros.com.dam.pmdm.tpv.ui.productos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.commons.ui.ImagenDesdePath
import ies.sequeros.com.dam.pmdm.tpv.aplicacion.productos.ProductoDTO
import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.hombre

@Composable
fun TpvProductoCard(
    item: ProductoDTO,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val imgPath = remember(item.imgPath) {
        mutableStateOf(if (item.imgPath.isNotEmpty()) item.imgPath else "")
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(180.dp), // Un poco más alta para mostrar el precio
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // Distribuye el espacio para dejar el precio abajo
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Imagen circular
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {

                    ImagenDesdePath(imgPath, Res.drawable.hombre, Modifier.fillMaxSize())
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Nombre del producto
                Text(
                    text = item.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Precio destacado
            Text(
                text = "${item.precio}€",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )

        }
    }
}