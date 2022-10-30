package io.ygdrasil.delaunator

import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.domain.VoronoiCell
import kotlin.reflect.KProperty


internal interface NeighboursProvider {
    operator fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): List<VoronoiGraph.Node>
}


data class VoronoiGraph(
    val nodes: List<Node>
) {
    class Node internal constructor(
        val index: Int,
        neighboursProvider: NeighboursProvider
    ) {
        val neighbours: List<Node> by neighboursProvider
    }

}

internal class VoronoiGraphStructure() : NeighboursProvider {
    val origins = mutableListOf<IPoint>()
    val neighbours  = mutableListOf<MutableList<Int>>()

    val nodes: List<VoronoiGraph.Node> by lazy {
        origins.indices.map { index ->
            VoronoiGraph.Node(index, this)
        }
    }

    override fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): List<VoronoiGraph.Node> {
        return neighbours[thisRef.index].map { nodes[it] }
    }

    fun getGraph(): VoronoiGraph {
        return VoronoiGraph(nodes)
    }
}

fun <T : IPoint> Delaunator<T>.getVoronoiGraph(): VoronoiGraph {
    return VoronoiGraphStructure().apply {
        origins.addAll(points)


        sequence {
            val seen = HashSet<Int>()  // of point ids
            for (triangleId in triangles.indices) {
                val id = triangles[nextHalfedgeIndex(triangleId)]
                if (!seen.contains(id)) {
                    seen.add(id)
                    val edges = edgesAroundPoint(triangleId)
                    val triangles = edges.map { x -> triangleOfEdge(x) }
                    val vertices = triangles.map { x -> getTriangleCenter(x) }
                    yield(VoronoiCell(id, vertices.toList()))
                }
            }
        }

    }.getGraph()

}