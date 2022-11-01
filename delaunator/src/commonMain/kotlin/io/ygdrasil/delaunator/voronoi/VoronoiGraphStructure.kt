package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.domain.IPoint
import kotlin.reflect.KProperty


internal class VoronoiGraphStructure : NeighboursProvider {
    internal val origins = mutableListOf<IPoint>()
    internal val neighbours  = mutableListOf<MutableList<Int>>()

    internal val nodes: List<VoronoiGraph.Node> by lazy {
        origins.mapIndexed { index, origin ->
            VoronoiGraph.Node(index.toUInt(), origin, this)
        }
    }

    override fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): List<VoronoiGraph.Node> {
        return neighbours[thisRef.index].map { nodes[it] }
    }

    fun getGraph(): VoronoiGraph {
        return VoronoiGraph(nodes)
    }
}
