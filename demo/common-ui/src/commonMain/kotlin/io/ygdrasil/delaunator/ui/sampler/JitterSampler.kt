package io.ygdrasil.delaunator.ui.sampler

import io.ygdrasil.delaunator.domain.Point
import kotlin.random.Random

object JitterSampler {

    fun sample(
        initX: Int,
        initY: Int,
        width: Int,
        height: Int,
        step: Int
    ): MutableList<Point> {
        val jitter = step / 4
        val points = mutableListOf<Point>()
        for (x in initX until (width + 1) step step) {
            for (y in initY until (height + 1) step step) {
                points.add(
                    Point(
                        x + jitter * (Random.nextDouble() - Random.nextDouble()),
                        y + jitter * (Random.nextDouble() - Random.nextDouble())
                    )
                )
            }
        }
        return points
    }
}