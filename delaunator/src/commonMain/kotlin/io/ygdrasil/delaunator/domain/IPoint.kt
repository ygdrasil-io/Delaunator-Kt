package io.ygdrasil.delaunator.domain

interface IPoint {
    var x: Double
    var y: Double

    fun toArray() = arrayOf(x, y)

    operator fun component1() = x
    operator fun component2() = y

}