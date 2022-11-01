package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.domain.IPoint
import kotlin.reflect.KProperty

internal inline operator fun <E> MutableList<E>.get(index: Index): E = this[index.toInt()]

internal class VoronoiGraphStructure : NeighboursProvider, NodeByVertexProvider {

    fun getGraph(): VoronoiGraph {
        return VoronoiGraph(nodes, vertices)
    }

    internal val origins = mutableListOf<IPoint>()
    internal val neighbours  = mutableListOf<MutableList<Int>>()
    internal val verticesByNode  = mutableListOf<MutableList<Int>>()
    internal val verticesPosition = mutableListOf<IPoint>()
    internal val nodesByVertex = mutableListOf<MutableList<Int>>()

    internal val nodes: Nodes by lazy {
        origins.mapIndexed { index, origin ->
            VoronoiGraph.Node(
                index.toUInt(),
                origin,
                this,
                verticesProvider
            )
        }
    }

    internal val vertices: Vertices by lazy {
        verticesPosition.mapIndexed { index, position ->
            VoronoiGraph.Node.Vertex(index.toUInt(), position, this)
        }
    }

    private val verticesProvider = object : VerticesProvider {
        override fun getValue(node: VoronoiGraph.Node, property: KProperty<*>): Vertices {
            return verticesByNode[node.index].map { vertices[it] }
        }
    }
    override fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): Nodes {
        return neighbours[thisRef.index].map { nodes[it] }
    }

    override fun getValue(thisRef: VoronoiGraph.Node.Vertex, property: KProperty<*>): Nodes {
        TODO("Not yet implemented")
    }
}
