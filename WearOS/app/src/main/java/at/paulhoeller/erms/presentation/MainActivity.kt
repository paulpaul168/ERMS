package at.paulhoeller.erms.presentation

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import at.paulhoeller.erms.presentation.theme.ERMSTheme
import kotlinx.coroutines.delay
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

data class Action(val name: String, val emoji: String);

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun WearApp() {
    val bleScanner = BleScanner(LocalContext.current as ComponentActivity, Handler());
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }

    val formattedTime = remember(currentTime) {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(currentTime)
    }

    ERMSTheme {
        val actions = listOf(
            Action("Danger", "👊"),
            Action("CPR", "🩺"),
            Action("Security", "👮‍♂️"),
            Action("Chair", "🪑"),
            Action("Stretcher", "🛏️"),
            Action("Ready", "✅"),
        )

        var selected by remember { mutableStateOf(-1) };

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SwipeGestureDetection(
                    onSwipeRight = {},
                    onSwipeUp = {
                        selected = (selected - 1).coerceAtLeast(-1);
                    },
                    onSwipeDown = {
                        selected = (selected + 1).coerceAtMost(actions.count() - 1);
                    },
                    onTap = {
                        val messageData = MessageData(
                            "1234343",
                            actions[selected].name,
                            listOf<BeaconData>(
                                BeaconData("1", LocationHelper.getLocation()),
                                BeaconData("2", LocationHelper.getLocation()),
                                BeaconData("3", LocationHelper.getLocation())
                            )
                        )
                        //bleScanner.scanLeDevice();
                        HttpPostTask("https://erms.stefhol.eu/api/v1/events", messageData).execute()
                    }
                )

                if (selected == -1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = formattedTime,
                            color = Color.White,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black)
                                .fillMaxHeight(0.65F),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = actions[selected].emoji,
                                color = Color.White,
                                fontSize = 56.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(Color.White),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(
                                text = actions[selected].name,
                                color = Color.Black,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

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
    onSwipeDown: () -> Unit,
    onTap: () -> Unit
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
                            abs(deltaX) < 50 && kotlin.math.abs(deltaY) < 50 -> onTap()
                        }
                        true
                    }

                    else -> false
                }
            }
    ) {
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