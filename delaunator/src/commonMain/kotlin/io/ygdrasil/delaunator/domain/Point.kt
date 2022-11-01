package io.ygdrasil.delaunator.domain

class Point(override var x: Double, override var y: Double) : IPoint {

    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y)
    }

    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun div(other: Int): Point {
        return Point(x / other, y / other)
    }

    override fun toString() = "{$x},{$y}"

    override fun equals(other: Any?) = (other as? Point)
        ?.let { it.x == x && it.y == y } == true

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

}