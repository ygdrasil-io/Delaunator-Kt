package io.ygdrasil.delaunator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldHaveLength
import io.ygdrasil.delaunator.domain.Point
import kotlin.random.Random

class DynamicTests : FunSpec({

    test("sam should be a three letter name") {
        val delaunator = Sampler.sample()
            .toDelaunator()
        "sam".shouldHaveLength(3)
    }

    test("pam should be a three letter name") {
        "pam".shouldHaveLength(3)
    }

    test("tim should be a three letter name") {
        "tim".shouldHaveLength(3)
    }
})

private fun MutableList<Point>.toDelaunator() = Delaunator(this)


object Sampler {

    fun sample(): MutableList<Point> {
        val width = 100
        val height = 100
        val step = 10
        val points = mutableListOf<Point>()
        for (x in 0..width step step) {
            for (y in 0 .. height step step) {
                points.add(Point(x.toDouble(), y.toDouble()))
            }
        }
        return points
    }
}