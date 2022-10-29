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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import io.ygdrasil.delaunator.Delaunator
import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.domain.Point
import io.ygdrasil.delaunator.ui.sampler.JitterSampler
import io.ygdrasil.delaunator.ui.sampler.UniformPoissonDiskSampler

const val canvasSize = 800
const val buttonHeight = 60

class Application {
    lateinit var canvas: DrawScope
    var delaunator: Delaunator<Point> = Delaunator(getPoisonDiscSample())

    fun getJitterSample(): MutableList<Point> {
        val step = 40
        return JitterSampler.sample(
            step.inv() * 2,
            step.inv() * 2,
            canvasSize + step * 2,
            canvasSize + step * 2,
            step
        )
    }

    fun getPoisonDiscSample(): MutableList<Point> {
        return UniformPoissonDiskSampler.sampleCircle(
            Point(canvasSize.toDouble() / 2, canvasSize.toDouble() / 2),
            canvasSize / 2 * 0.80,
            40.0
        )
    }

    fun redrawCanvas() {
        clearCanvas()
        drawDelaunay()
        drawVoronoi()
        drawHull()
        drawPoints()
    }

    private fun drawPoints() {
        delaunator.points
            .forEach { point ->
                drawCircle(point.rasterize(), Color.Red)
            }
    }

    private fun drawDelaunay() {
        delaunator.getEdges()
            .forEach { edge ->
                drawLine(edge.p.rasterize(), edge.q.rasterize(), Color.Black)
            }
    }

    private fun drawLine(start: IPoint, end: IPoint, color: Color) {
        val pixelDensity = canvas.size.width / canvasSize
        canvas.drawLine(
            color,
            Offset(start.x.toFloat(), start.y.toFloat()),
            Offset(end.x.toFloat(), end.y.toFloat()),
            4.0f * pixelDensity
        )
    }

    private fun drawVoronoi() {
        val cells = delaunator.getVoronoiCells()
        cells.forEach { cell ->

            val points = cell.points
                .map { point -> point.toOffset() }
                .toList()
            canvas.drawPoints(
                points,
                PointMode.Polygon,
                Color.White,
                strokeWidth = 1.0f
            )
        }

        cells.forEach { cell ->
            cell.points.forEach { point ->
                drawCircle(point.rasterize(), Color.Blue)
            }
        }
    }

    private fun drawCircle(point: IPoint, color: Color) {
        val pixelDensity = canvas.size.width / canvasSize
        val radius = 4.0f * pixelDensity
        canvas.drawCircle(
            color,
            center = Offset((point.x - radius / 2).toFloat(), (point.y - radius / 2).toFloat()),
            radius = radius
        )
    }

    private fun drawHull() {
        for (edge in delaunator.getHullEdges()) {
            drawLine(edge.p.rasterize(), edge.q.rasterize(), Color(0xFFFFA500)) // Color Orange
        }
    }

    private fun clearCanvas() {
        canvas.drawRect(Color(0xFF474747))
    }


    private fun IPoint.rasterize(): IPoint {
        val pixelDensity = canvas.size.width / canvasSize
        return Point(x * pixelDensity, y * pixelDensity)
    }

    private fun IPoint.toOffset() = rasterize()
            .let { Offset(it.x.toFloat(), it.y.toFloat()) }

}



fun main() = singleWindowApplication(
    title = "Delaunator Kt",
    state = WindowState(width = canvasSize.dp, height = (canvasSize + buttonHeight).dp)) {
    MaterialTheme {
        applicationLayout()
    }
}

@Composable
fun applicationLayout() {
    val application = remember { Application() }
    Column(Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.size(canvasSize.dp, canvasSize.dp),
            onDraw = {
                application.canvas = this
                application.redrawCanvas()
            })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.size(canvasSize.dp, buttonHeight.dp)
        ) {

            Button(onClick = {
                val points = application.getPoisonDiscSample()
                application.delaunator = Delaunator(points)
            }, modifier = Modifier.size((canvasSize / 2).dp, buttonHeight.dp)) {
                Text("generate poisson sample")
            }
            Button(onClick = {
                val points = application.getJitterSample()
                application.delaunator = Delaunator(points)
            }, modifier = Modifier.size((canvasSize / 2).dp, buttonHeight.dp)) {
                Text("generate jitter sample")
            }
        }
    }
}