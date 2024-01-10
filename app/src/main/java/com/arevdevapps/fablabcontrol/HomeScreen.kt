import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arevdevapps.fablabcontrol.R

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var dialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "FABLAB Control",
            modifier = Modifier.padding(bottom = 12.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 33.sp,
                color = Color(android.graphics.Color.parseColor("#264385"))
            )
        )
        Text(
            "Bienvenido a la aplicación para control arduino del FABLAB",
            modifier = Modifier.padding(bottom = 12.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor("#898989"))
            )
        )


        // Botón "Más información"
        Button(
            onClick = { dialogVisible = true },
            modifier = Modifier.padding(top = 25.dp)
        ) {
            Text("Más información")
        }

        // AlertDialog
        if (dialogVisible) {
            AlertDialog(
                containerColor = Color.White,
                title = { Text("Información de la App") },
                text = { Text("Esta aplicación fue construida por un estudiante de ESFE y club de Robótica pertenecientes al FABLAB, para más información, código fuente y actualizaciones revisa el siguiente enlace") },
                onDismissRequest = {
                    dialogVisible = false
                },

                confirmButton = {
                    Button(
                        onClick = {
                            // Abre el enlace cuando se hace clic en "Ir al link"
                            val uri = Uri.parse("https://github.com/arev-dev/fablab-control-app-compose") // Reemplaza con tu enlace
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        }
                    ) {
                        Text("Ir al link")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        dialogVisible = false
                    }) {
                        Text("Cerrar")
                    }
                }
            )
        }
        Image(
            painter = painterResource(id = R.drawable.fablab_group_1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(1.0f),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )

    }
}
