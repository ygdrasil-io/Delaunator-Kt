package io.ygdrasil.delaunator.ui

import io.ygdrasil.delaunator.Point

typealias Color = Array<Int>
typealias CanvasSize = Pair<Int, Int>

private val blackColor = arrayOf(0, 0, 0)
private val redColor = arrayOf(255, 0, 0)
private val darkGreyColor = arrayOf(71, 71, 71)
private val blueColor = arrayOf(0, 0, 255)
private val whiteColor = arrayOf(255, 255, 255)
private val orangeColor = arrayOf(255, 165, 0)

val hullColor = orangeColor
val polygonColor = whiteColor
val vertexColor = blueColor
val lineColor = blackColor
val backgroundColor = darkGreyColor
val pointColor = redColor

class ApplicationDrawer(
    private val drawer: CanvasDrawer,
    private val applicationState: ApplicationState
) {

    interface CanvasDrawer {

        fun clearCanvas(color: Color)
        fun drawLine(from: Array<Double>, to: Array<Double>, color: Color)
        fun getCanvasSize(): CanvasSize
        fun drawCircle(center: Array<Double>, radius: Double, color: Color)
        fun drawPolygon(vertices: List<Array<Double>>, color: Color)

    }

    fun draw() {
        drawer.clearCanvas(backgroundColor)
        drawDelaunay()
        drawVoronoi()
        drawHull()
        drawPoints()
    }

    private fun drawHull() {
        applicationState.hullEdges
            .map { (left, right) -> left.rasterize() to right.rasterize() }
            .forEach { (from, to) ->
                drawer.drawLine(from, to, hullColor)
            }
    }

    private fun drawVoronoi() {
        applicationState.voroinoiGraph
            .nodes
            .map { node -> node.vertices.map { it.position }.map { it.rasterize() } }
            .forEach { vertices ->
                drawer.drawPolygon(vertices, polygonColor)
            }

        applicationState.voroinoiGraph
            .vertices
            .map { it.position }
            .forEach { point ->
                drawer.drawCircle(point.rasterize(), 4.0, vertexColor)
            }
    }

    private fun drawPoints() {
        applicationState.voroinoiGraph
            .nodes.map { it.origin }
            .forEach { point ->
                drawer.drawCircle(point.rasterize(), 4.0, pointColor)
            }
    }

    private fun drawDelaunay() {
        applicationState.voroinoiGraph
            .nodes.forEach { node ->
                val from = node.origin.rasterize()
                node.neighbours.map { it.origin.rasterize() }
                    .forEach { to ->
                        drawer.drawLine(from, to, lineColor)
                    }
            }
    }

    private fun Point.rasterize(): Array<Double> = drawer.getCanvasSize()
        .toPixelDensity()
        .let { (xPixelDensity, yPixelDensity) ->
            arrayOf(x * xPixelDensity, y * yPixelDensity)
        }

    private fun Pair<Int, Int>.toPixelDensity(): Pair<Double, Double> {
        val (canvasWidth, canvasHeight) = this
        return (canvasWidth / applicationState.canvasWidth.toDouble()) to (canvasHeight / applicationState.canvasHeight.toDouble())

    }

}

