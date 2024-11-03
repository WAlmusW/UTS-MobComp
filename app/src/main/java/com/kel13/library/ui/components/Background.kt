import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.kel13.library.R

@Composable
fun RepeatingPatternBackground() {
    val context = LocalContext.current
    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_wallpaper)

    val patternBitmap = bitmap.asImageBitmap()

    Canvas(modifier = Modifier.fillMaxSize()) {
        val tileWidth = patternBitmap.width.toFloat()
        val tileHeight = patternBitmap.height.toFloat()

        var x = 0f
        while (x < size.width) {
            var y = 0f
            while (y < size.height) {
                drawImage(
                    image = patternBitmap,
                    topLeft = Offset(x, y)
                )
                y += tileHeight
            }
            x += tileWidth
        }
    }
}

