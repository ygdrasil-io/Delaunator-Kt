package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.domain.IPoint
import kotlin.reflect.KProperty

internal operator fun <E> List<E>.get(index: Index): E = this[index.toInt()]

internal class VoronoiGraphStructure(
    internal val origins: List<IPoint>,
    internal val verticesByNode: List<List<Int>>,
    internal val neighboursNodesByNode: List<List<Int>>,
    internal val verticesPosition: List<IPoint>,
    internal val neighboursVertexByVertex: List<List<Int>>,
    internal val neighboursNodesByVertex: List<List<Int>>
) : NeighboursProvider {

    fun getGraph(): VoronoiGraph {
        return VoronoiGraph(nodes, vertices)
    }

    private val nodes: Nodes by lazy {
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
            VoronoiGraph.Node.Vertex(
                index.toUInt(),
                position,
                neighboursNodesOfVertexProvider,
                neighboursVerticesOfVertexProvider
            )
        }
    }

    private val neighboursVerticesOfVertexProvider = object : NeighboursVerticesOfVertexProvider {
        override fun getValue(vertex: VoronoiGraph.Node.Vertex, property: KProperty<*>): Vertices {
            return neighboursVertexByVertex[vertex.index]
                // when node is on edge of the graph, a vertix can contains less than 3 neighbours
                .filter { it != -1 }
                .map { vertices[it] }
        }
    }

    private val neighboursNodesOfVertexProvider = object : NeighboursNodesOfVertexProvider {
        override fun getValue(vertex: VoronoiGraph.Node.Vertex, property: KProperty<*>): Nodes {
            return neighboursNodesByVertex[vertex.index].map { nodes[it] }
        }
    }

    private val verticesProvider = object : VerticesProvider {
        override fun getValue(node: VoronoiGraph.Node, property: KProperty<*>): Vertices {
            return verticesByNode[node.index].map { vertices[it] }
        }
    }

    override fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): Nodes {
        return neighboursNodesByNode[thisRef.index].map { nodes[it] }
    }

}
