package io.ygdrasil.delaunator.ui

import com.soywiz.korge.Korge
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.sceneContainer
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.vector.ShapeBuilder
import com.soywiz.korma.geom.vector.circle
import com.soywiz.korma.geom.vector.line
import io.ygdrasil.delaunator.Delaunator
import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.domain.Point
import io.ygdrasil.delaunator.ui.sampler.JitterSampler
import io.ygdrasil.delaunator.ui.sampler.UniformPoissonDiskSampler

const val canvasSize = 800
const val buttonHeight = 60

suspend fun main() =
    Korge(width = canvasSize, height = canvasSize, bgcolor = Colors.BLACK, title = "Delaunator Demo") {
        val sceneContainer = sceneContainer()

        sceneContainer.changeTo({ MyScene() })
    }

class MyScene : Scene() {

    val application = Application()

    override suspend fun SContainer.sceneMain() {
        application.canvas = fixedSizeContainer(canvasSize.toDouble(), canvasSize - buttonHeight.toDouble(), true)
        application.redrawCanvas()

        uiButton(
            "generate poisson sample",
            width = canvasSize / 2.0,
            height = buttonHeight.toDouble()
        ) {
            position(.0, canvasSize - height)
            onClick {
                val points = application.getPoisonDiscSample()
                application.delaunator = Delaunator(points)
                application.redrawCanvas()
            }
            enable()
        }

        uiButton(
            "generate jitter sample",
            width = canvasSize / 2.0,
            height = buttonHeight.toDouble()
        ) {
            position(width, canvasSize - height)
            onClick {
                val points = application.getJitterSample()
                application.delaunator = Delaunator(points)
                application.redrawCanvas()
            }
            enable()
        }
    }
}

class Application {
    lateinit var canvas: Container
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
        canvas.graphics {
            clearCanvas()
            drawDelaunay()
            drawVoronoi()
            drawHull()
            drawPoints()
        }
    }

    private fun ShapeBuilder.drawPoints() {
        delaunator.points
            .forEach { point ->
                drawCircle(point.rasterize(), Colors.RED)
            }
    }

    private fun ShapeBuilder.drawDelaunay() {
        delaunator.getEdges()
            .forEach { edge ->
                drawLine(edge.p.rasterize(), edge.q.rasterize(), Colors.BLACK)
            }
    }

    private fun ShapeBuilder.drawLine(start: IPoint, end: IPoint, color: RGBA) {
        val pixelDensity = canvas.width / canvasSize
        stroke(color) {
            lineWidth = 4.0f * pixelDensity
            line(start.x, start.y, end.x, end.y)
        }
    }

    private fun ShapeBuilder.drawVoronoi() {
        val cells = delaunator.getVoronoiCells()
        cells.forEach { cell ->

            val points = cell.points
                .map { point -> point.rasterize() }
                .toList()

            stroke(Colors.WHITE) {
                points.forEachIndexed { index, point ->
                    val (x, y) = point.x to point.y
                    when (index) {
                        0 -> moveTo(x, y)
                        else -> lineTo(x, y)
                    }
                }
            }

        }

        cells.forEach { cell ->
            cell.points.forEach { point ->
                drawCircle(point.rasterize(), Colors.BLUE)
            }
        }
    }

    private fun ShapeBuilder.drawCircle(point: IPoint, color: RGBA) {
        val pixelDensity = canvas.width / canvasSize
        val radius = 4.0f * pixelDensity

        fill(color) {
            circle(point.x - radius / 2, point.y - radius / 2, radius)
        }

    }

    private fun ShapeBuilder.drawHull() {
        for (edge in delaunator.getHullEdges()) {
            drawLine(edge.p.rasterize(), edge.q.rasterize(), RGBA(255, 165, 0)) // Color Orange
        }
    }

    private fun ShapeBuilder.clearCanvas() {
        clear()
        fill(RGBA(71, 71, 71)) {
            fillRect(0, 0, canvas.width, canvas.height)
        }
    }


    private fun IPoint.rasterize(): IPoint {
        val pixelDensity = canvas.width / canvasSize
        return Point(x * pixelDensity, y * pixelDensity)
    }

}


/*
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
}*/