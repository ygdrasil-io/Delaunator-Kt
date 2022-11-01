package io.ygdrasil.delaunator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication

const val canvasSize = 800
const val buttonHeight = 60

fun main() = singleWindowApplication(
    title = "Delaunator Demo",
    state = WindowState(width = canvasSize.dp, height = (canvasSize + buttonHeight).dp)) {
    MaterialTheme {
        applicationLayout()
    }
}

@Composable
fun applicationLayout() {
    val applicationState = remember { ApplicationState(canvasSize) }
    Column(Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.size(canvasSize.dp, canvasSize.dp),
            onDraw = {
                val drawer = JetpackDrawer(this)
                ApplicationDrawer(drawer, applicationState)
                    .draw()
            })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.size(canvasSize.dp, buttonHeight.dp)
        ) {

            Button(onClick = {
                applicationState.regenerate(ApplicationState.Sampler.PoisonDisc)
            }, modifier = Modifier.size((canvasSize / 2).dp, buttonHeight.dp)) {
                Text("generate poisson sample")
            }
            Button(onClick = {
                applicationState.regenerate(ApplicationState.Sampler.Jitter)
            }, modifier = Modifier.size((canvasSize / 2).dp, buttonHeight.dp)) {
                Text("generate jitter sample")
            }
        }
    }
}

