package io.ygdrasil.delaunator.ui

import io.ygdrasil.delaunator.Delaunator
import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.domain.Point
import io.ygdrasil.delaunator.ui.sampler.JitterSampler
import io.ygdrasil.delaunator.ui.sampler.UniformPoissonDiskSampler
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.input.MouseButton
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

class FXApp : Application() {
    private lateinit var delaunator: Delaunator<Point>
    private lateinit var graphicsContext: GraphicsContext
    private lateinit var canvas: Canvas

    override fun start(primaryStage: Stage) {
        val root = VBox()

        addCanvas(root)

        val buttonPanel = HBox()
        root.children.add(buttonPanel)

        Button("generate poisson sample").apply {
            onAction = EventHandler {
                val points = getPoisonDiscSample()
                delaunator = Delaunator(points)
                redrawCanvas()
            }
            prefWidth = canvas.width / 2
            buttonPanel.children.add(this)
        }

        Button("generate jitter sample").apply {
            onAction = EventHandler {
                val points = getJitterSample()
                delaunator = Delaunator(points)
                redrawCanvas()
            }
            prefWidth = canvas.width / 2
            prefHeight = 25.0
            buttonPanel.children.add(this)
        }

        val scene = Scene(root, canvas.width, canvas.height + 25.0, Color.BLACK)
        primaryStage.title = "Delaunator Kt"
        primaryStage.scene = scene
        primaryStage.show()
    }

    private fun addCanvas(root: Pane) {
        canvas = Canvas(800.0, 800.0)
        root.children.add(canvas)
        graphicsContext = canvas.graphicsContext2D

        // Add action to add point on click
        canvas.onMouseClicked = EventHandler { event ->
            val mousePoint = Point(event.sceneX, event.sceneY)
            if (event.button == MouseButton.PRIMARY) {
                addPoint(mousePoint)
            }
        }

        // Init delaunator and render
        val points = getPoisonDiscSample()
        delaunator = Delaunator(points)
        redrawCanvas()
    }

    private fun getJitterSample(): MutableList<Point> {
        val step = 40
        return JitterSampler.sample(
            step.inv() * 2,
            step.inv() * 2,
            canvas.width.toInt() + step * 2,
            canvas.height.toInt() + step * 2,
            step
        )
    }

    private fun getPoisonDiscSample(): MutableList<Point> {
        return UniformPoissonDiskSampler.sampleCircle(
            Point(canvas.width / 2, canvas.height / 2),
            canvas.width / 2 * 0.80,
            40.0
        )
    }

    private fun addPoint(point: Point) {
        val points = delaunator.points.toMutableList()
        points.add(point)
        delaunator = Delaunator(points)
        redrawCanvas()
    }

    private fun redrawCanvas() {
        clearCanvas()
        drawDelaunay()
        drawVoronoi()
        drawHull()
        drawPoints()
    }

    private fun drawPoints() {
        delaunator.points
            .forEach { point ->
                drawCircle(point, Color.RED)
            }
    }

    private fun drawDelaunay() {
        delaunator.getEdges()
            .forEach { edge ->
                drawLine(edge.p, edge.q, Color.BLACK)
            }
    }

    private fun drawLine(start: IPoint, end: IPoint, color: Color) {
        graphicsContext.stroke = color
        graphicsContext.lineWidth = 4.0
        graphicsContext.strokeLine(start.x, start.y, end.x, end.y)
    }

    private fun drawVoronoi() {
        val cells = delaunator.getVoronoiCells()
        cells.forEach { cell ->
            graphicsContext.stroke = Color.WHITE
            val xCoordinate = DoubleArray(cell.points.size)
            val yCoordinate = DoubleArray(cell.points.size)
            cell.points.forEachIndexed { index, point -> xCoordinate[index] = point.x }
            cell.points.forEachIndexed { index, point -> yCoordinate[index] = point.y }
            graphicsContext.lineWidth = 1.0
            graphicsContext.strokePolygon(xCoordinate, yCoordinate, xCoordinate.size)
        }

        cells.forEach { cell ->
            cell.points.forEach { point ->
                drawCircle(point, Color.BLUE)
            }
        }
    }

    private fun drawCircle(point: IPoint, color: Color) {
        val radius = 8.0
        graphicsContext.fill = color
        graphicsContext.fillOval(point.x - radius / 2, point.y - radius / 2, radius, radius)
    }

    private fun drawHull() {
        for (edge in delaunator.getHullEdges()) {
            drawLine(edge.p, edge.q, Color.ORANGE)
        }
    }

    private fun clearCanvas() {
        graphicsContext.fill = Color.rgb(71, 71, 71)
        graphicsContext.fillRect(.0, .0, canvas.width, canvas.height)
    }

}

fun main(args: Array<String>) {
    Application.launch(FXApp::class.java, *args)
}
