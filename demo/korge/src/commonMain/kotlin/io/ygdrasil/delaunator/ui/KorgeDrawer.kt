package io.ygdrasil.delaunator.ui

import com.soywiz.korge.view.FixedSizeContainer
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.vector.ShapeBuilder
import com.soywiz.korma.geom.vector.circle
import com.soywiz.korma.geom.vector.line
import io.ygdrasil.delaunator.ui.Color as DelaunatorColor

class KorgeDrawer(
    private val shapeBuilder: ShapeBuilder,
    private val container: FixedSizeContainer
) : ApplicationDrawer.CanvasDrawer {

    override fun clearCanvas(color: DelaunatorColor) {
        shapeBuilder.clear()
        shapeBuilder.fill(color.toColor()) {
            shapeBuilder.fillRect(0, 0, container.width, container.height)
        }
    }

    override fun drawLine(from: Array<Double>, to: Array<Double>, color: DelaunatorColor) {
        shapeBuilder.stroke(color.toColor()) {
            shapeBuilder.lineWidth = 4.0
            shapeBuilder.line(from[0], from[1], to[0], to[1])
        }
    }

    override fun getCanvasSize(): CanvasSize {
        return container.width.toInt() to container.height.toInt()
    }

    override fun drawCircle(center: Array<Double>, radius: Double, color: DelaunatorColor) {
        shapeBuilder.fill(color.toColor()) {
            shapeBuilder.circle(center[0], center[1], radius)
        }
    }

    override fun drawPolygon(vertices: List<Array<Double>>, color: DelaunatorColor) {
        shapeBuilder.stroke(color.toColor()) {
            vertices.forEachIndexed { index, (x, y) ->
                when (index) {
                    0 -> shapeBuilder.moveTo(x, y)
                    else -> shapeBuilder.lineTo(x, y)
                }
            }
        }
    }

    private fun DelaunatorColor.toColor() = RGBA(this[0], this[1], this[2])
}