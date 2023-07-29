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
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
        var showMessage by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }

        Surface(color = MaterialTheme.colors.background) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Center Text", style = TextStyle(fontSize = 24.sp))

                SwipeGestureDetection(
                    onSwipeRight = {
                        message = "Right button pressed"
                        showMessage = true
                    },
                    onSwipeUp = {
                        message = "Up button pressed"
                        showMessage = true
                    },
                    onSwipeDown = {
                        message = "Down button pressed"
                        showMessage = true
                    }
                )

                if (showMessage) {
                    ShowToast(message = message) {
                        showMessage = false
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
    val context = LocalContext.current

    var startY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startY = event.y
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        val deltaY = event.y - startY
                        when {
                            deltaY < -50 -> onSwipeUp()
                            deltaY > 50 -> onSwipeDown()
                            else -> onSwipeRight()
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