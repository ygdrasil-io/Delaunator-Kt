package io.ygdrasil.delaunator.voronoi

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotHaveSize
import io.ygdrasil.delaunator.Sampler
import io.ygdrasil.delaunator.toDelaunator

val voronoiGraphKtTest: StringSpec.() -> Unit = {

    val sample = Sampler.sample()
    val delaunator = sample.toDelaunator()
    val voronoiGraph = delaunator.toVoronoiGraph()

    "voronoi graph nodes should match number of point on sampler" {
        voronoiGraph.nodes.shouldHaveSize(sample.size)
    }

    "voronoi graph nodes should contains at list one neighbours" {
        voronoiGraph.nodes.forEach { node ->
            node.neighbours.shouldNotHaveSize(0)
        }
    }

    "voronoi graph nodes neighbours should be bidirectional" {
        voronoiGraph.nodes.forEach { node ->
            node.neighbours.forEach { neighbour ->
                neighbour.neighbours.shouldContain(node)
            }
        }
    }

    "each voronoi graph nodes neighbours should share two vertices" {
        TODO()
    }

}
