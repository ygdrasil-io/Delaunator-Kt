package io.ygdrasil.delaunator.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.ygdrasil.delaunator.ui.Color as DelaunatorColor


class JetpackDrawer(private val drawScope: DrawScope) : ApplicationDrawer.CanvasDrawer {
    override fun clearCanvas(color: DelaunatorColor) {
        drawScope.drawRect(color.toColor())
    }

    override fun drawLine(from: Array<Double>, to: Array<Double>, color: DelaunatorColor) {
        drawScope.drawLine(
            color.toColor(),
            from.toOffset(),
            to.toOffset(),
            4.0f
        )
    }

    override fun getCanvasSize(): CanvasSize {
        return drawScope.size.width.toInt() to drawScope.size.height.toInt()
    }

    override fun drawCircle(center: Array<Double>, radius: Double, color: DelaunatorColor) {
        drawScope.drawCircle(
            color.toColor(),
            center = center.toOffset(),
            radius = radius.toFloat()
        )
    }

    override fun drawPolygon(vertices: List<Array<Double>>, color: DelaunatorColor) {
        drawScope.drawPoints(
            vertices.map { it.toOffset() },
            PointMode.Polygon,
            color.toColor(),
            strokeWidth = 1.0f
        )
    }

    private fun DelaunatorColor.toColor() = Color(this[0], this[1], this[2])
    private fun Array<Double>.toOffset() = Offset(this[0].toFloat(), this[1].toFloat())

}
