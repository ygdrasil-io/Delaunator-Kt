package io.ygdrasil.delaunator.ui

import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.domain.Point
import io.ygdrasil.delaunator.toDelaunator
import io.ygdrasil.delaunator.ui.sampler.JitterSampler
import io.ygdrasil.delaunator.ui.sampler.UniformPoissonDiskSampler
import io.ygdrasil.delaunator.voronoi.toVoronoiGraph
import kotlin.math.min

class ApplicationState(canvasSize: Int) {
    val canvasWidth = canvasSize
    val canvasHeight = canvasSize

    enum class Sampler {
        PoisonDisc,
        Jitter
    }

    lateinit var hullEdges:List<Pair<IPoint, IPoint>>
    var voroinoiGraph = getGraphFromSampler(Sampler.PoisonDisc)

    fun regenerate(sampler: Sampler) {
        voroinoiGraph = getGraphFromSampler(sampler)
    }

    private fun getGraphFromSampler(sampler: Sampler) = when (sampler) {
        Sampler.PoisonDisc -> getPoisonDiscSample()
        Sampler.Jitter -> getJitterSample()
    }.toDelaunator().apply {
        hullEdges = getHullEdges().map { it.p to it.q }
    }.toVoronoiGraph()

    private fun getPoisonDiscSample(): MutableList<Point> {
        return UniformPoissonDiskSampler.sampleCircle(
            Point(canvasWidth.toDouble() / 2, canvasHeight.toDouble() / 2),
            min(canvasWidth, canvasHeight) / 2 * 0.80,
            40.0
        )
    }

    private fun getJitterSample(): MutableList<Point> {
        val step = 40
        return JitterSampler.sample(
            step.inv() * 2,
            step.inv() * 2,
            canvasWidth + step * 2,
            canvasHeight + step * 2,
            step
        )
    }
}
