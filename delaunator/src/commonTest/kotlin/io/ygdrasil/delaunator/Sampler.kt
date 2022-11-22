package io.ygdrasil.delaunator

import io.ygdrasil.delaunator.Point


object Sampler {

    fun sample() = mutableListOf<Point>().apply {
        val width = 100
        val height = 100
        val step = 10
        for (x in 0..width step step) {
            for (y in 0 .. height step step) {
                add(Point(x.toDouble(), y.toDouble()))
            }
        }
    }
}