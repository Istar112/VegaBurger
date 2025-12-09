package ies.sequeros.com.dam.pmdm.commons.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import org.jetbrains.compose.resources.DrawableResource
import java.io.File

//@Composable
//actual fun ImagenDesdePath(
//    path: State<String>,
//    default: DrawableResource,
//    modifier: Modifier
//) {
//val file= File(path.value)
//    if(file.exists()){
//
//    AsyncImage(
//        model = ImageRequest.Builder(LocalPlatformContext.current)
//            .data(file)
//            .memoryCachePolicy(CachePolicy.DISABLED)
//            .diskCachePolicy(CachePolicy.DISABLED)
//            .build(),
//        contentDescription = null,
//        modifier = modifier
//    )}
//    else{
//        AsyncImage(
//            model = file,
//            contentDescription = null,
//            modifier = modifier
//        )
//    }
//}
@Composable
actual fun ImagenDesdePath(
    path: State<String>,
    default: DrawableResource,
    modifier: Modifier
) {
    val context = LocalPlatformContext.current
    val file = File(path.value)

    val model = if (file.exists()) {
        ImageRequest.Builder(context)
            .data(file)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
    } else {
        // 👉 usamos el default REAL
        ImageRequest.Builder(context)
            .data(default)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
    }

    AsyncImage(
        model = model,
        contentDescription = null,
        modifier = modifier
    )
}
