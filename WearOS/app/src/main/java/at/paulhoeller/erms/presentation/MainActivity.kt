/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package at.paulhoeller.erms.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import at.paulhoeller.erms.presentation.theme.ERMSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}



@Composable
fun WearApp() {
    ERMSTheme {
        val buttonList = listOf(
            "Gefahr für sich selbst",
            "Reanimation",
            "Gefahr für andere",
            "Sessel anfragen",
            "Trage anfragen",
            "Status: Bereit"
        )

        Surface(color = MaterialTheme.colors.background) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(vertical = 60.dp)
                ) {
                    itemsIndexed(buttonList) { index, buttonText ->

                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                            ) {
                                Text(buttonText, style = TextStyle(fontSize = 16.sp))
                            }


                    }
                }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SwipeGestureDetection(
    onSwipeRight: () -> Unit,
    onSwipeUp: () -> Unit,
    onSwipeDown: () -> Unit
) {
    var startX by remember { mutableStateOf(0f) }
    var startY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        val deltaX = event.x - startX
                        val deltaY = event.y - startY
                        when {
                            deltaY < -50 -> onSwipeDown()
                            deltaY > 50 -> onSwipeUp()
                            deltaX < -50 -> onSwipeRight()
                        }
                        true
                    }

                    else -> false
                }
            }
    ) {
        // Buttons and other UI elements can be placed here as before
    }
}

@Composable
fun ShowToast(message: String, onDismiss: () -> Unit) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
    onDismiss()
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}