package io.ygdrasil.delaunator

import io.ygdrasil.delaunator.domain.Point
import javafx.event.EventHandler
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseButton
import javafx.scene.paint.Color
import tornadofx.*
import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.domain.VoronoiCell
import io.ygdrasil.delaunator.sampler.JitterSampler
import io.ygdrasil.delaunator.sampler.UniformPoissonDiskSampler
import kotlin.math.sqrt


class MainWindow : View("Delaunator Kt") {

    private lateinit var delaunator: Delaunator<Point>
    private lateinit var graphicsContext: GraphicsContext
    private var selectedCell: VoronoiCell? = null

    val canvas = canvas {
        width = 800.0
        height = 800.0
        graphicsContext = graphicsContext2D

        onMouseClicked = EventHandler { event ->
            val mousePoint = Point(event.sceneX, event.sceneY)
            if (event.button == MouseButton.SECONDARY) {
                addPoint(mousePoint)
            } else if (event.button == MouseButton.PRIMARY) {
                val points = delaunator.points
                selectedCell = delaunator.getVoronoiCells()
                    .minBy { cell -> points[cell.index].dist(mousePoint) }
                redrawCanvas()

            }
        }

    }

    override val root = vbox {
        add(canvas)
        hbox {
            button("generate poisson sample") {
                onAction = EventHandler {
                    val points = getPoisonDiscSample()
                    delaunator = Delaunator(points)
                    redrawCanvas()
                }
                prefWidth = canvas.width / 2
            }
            button("generate jitter sample") {
                onAction = EventHandler {
                    val points = getJitterSample()
                    delaunator = Delaunator(points)
                    redrawCanvas()
                }
                prefWidth = canvas.width / 2
            }
        }
        val points = getPoisonDiscSample()
        delaunator = Delaunator(points)
        redrawCanvas()
    }

    private fun getJitterSample(): ArrayList<Point> {
        val step = 40
        return JitterSampler.sample(step.inv() * 2, step.inv() * 2, canvas.width.toInt() + step * 2, canvas.height.toInt() + step * 2, step)
    }

    private fun getPoisonDiscSample(): ArrayList<Point> {
        return UniformPoissonDiskSampler.sampleCircle(
            Point(canvas.width / 2, canvas.height / 2),
            canvas.width / 2 * 0.80,
            40.0
        )
    }

    private fun addPoint(point: Point) {
        val points = delaunator.points
        points.add(point)
        delaunator = Delaunator(points)
        redrawCanvas()
    }

    private fun redrawCanvas() {
        clearCanvas()
        drawSelectedCell()
        drawDelaunay()
        drawVoronoi()
        drawHull()
        drawPoints()
    }

    private fun drawSelectedCell() {
        selectedCell?.let { cell ->
            graphicsContext.fill = Color.AQUA
            val xCoordinate = DoubleArray(cell.points.size)
            val yCoordinate = DoubleArray(cell.points.size)
            cell.points.forEachIndexed { index, point -> xCoordinate[index] = point.x }
            cell.points.forEachIndexed { index, point -> yCoordinate[index] = point.y }
            graphicsContext.fillPolygon(xCoordinate, yCoordinate, xCoordinate.size)
        }
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
                drawLine(edge.p, edge.q, Color.BLACK);
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

    private fun Point.dist(that: Point): Double {
        return sqrt((this.x - that.x) * (this.x - that.x) + (this.y - that.y) * (this.y - that.y))
    }

}