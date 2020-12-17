package io.ygdrasil.delaunator.domain

class Point(override var x: Double, override var y: Double) : IPoint {

    override fun toString() = "{$x},{$y}"

    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y)
    }

    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun div(other: Int): Point {
        return Point(x / other, y / other)
    }

}