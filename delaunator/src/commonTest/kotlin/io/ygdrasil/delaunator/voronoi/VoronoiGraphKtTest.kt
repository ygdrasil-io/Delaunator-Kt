package io.ygdrasil.delaunator.voronoi

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.*
import io.ygdrasil.delaunator.Sampler
import io.ygdrasil.delaunator.toDelaunator

val voronoiGraphTest: StringSpec.() -> Unit = {

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

    "voronoi graph nodes should have vertices" {
        voronoiGraph.nodes.map { it.vertices }
            .forEach { it.shouldNotHaveSize(0) }
    }

    "each voronoi graph nodes neighbours should share two vertices, except on the edge of the diagram" {
        voronoiGraph.nodes.forEach { node ->
            node.neighbours.forEach { neighbour ->
                node.vertices
                    .filter { neighbour.vertices.contains(it) }
                    .shouldHaveAtMostSize(2)
            }
        }
    }

    "each voronoi graph vertex neighbours should be in found in 1 to 2 neighbours node" {
        voronoiGraph.vertices.forEach { vertex ->
            println("from $vertex\n")
            vertex.neighboursVertices.forEach { neighbourVertex ->
                println("checking $neighbourVertex\n")
                println("with neighbours nodes ${vertex.neighboursNodes}\n")
                vertex.neighboursNodes
                    .onEach { println("$it test ${it.vertices}") }
                    .filter { node -> node.vertices.contains(neighbourVertex) }
                    .shouldHaveAtLeastSize(1)
            }
        }
    }

    "each vertex should have from 1 to 3 neighbours when graph contains more than 3 points" {
        voronoiGraph.vertices.forEach { vertex ->
            vertex.neighboursVertices
                .shouldHaveAtMostSize(3)
                .shouldHaveAtLeastSize(1)
        }

    }
}
