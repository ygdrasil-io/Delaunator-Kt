package io.ygdrasil.delaunator.ui.sampler

import io.ygdrasil.delaunator.domain.Point

object JitterSampler {

    fun sample(
        initX: Int,
        initY: Int,
        width: Int,
        height: Int,
        step: Int
    ): ArrayList<Point> {
        val jitter = step / 4
        val points = ArrayList<Point>()
        for (x in initX until (width + 1) step step) {
            for (y in initY until (height + 1) step step) {
                points.add(
                    Point(
                        x + jitter * (Math.random() - Math.random()),
                        y + jitter * (Math.random() - Math.random())
                    )
                )
            }
        }
        return points
    }
}