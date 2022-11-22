package io.ygdrasil.delaunator

import kotlin.jvm.JvmInline

@JvmInline
value class Point(private val value: DoubleArray) {

    constructor(x: Double, y: Double) : this(doubleArrayOf(x, y))

    val x: Double
        get() = value[0]
    val y: Double
        get() = value[1]

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

    operator fun component1(): Double = x
    operator fun component2(): Double = y

}