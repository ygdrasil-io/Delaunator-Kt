package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.domain.IPoint

typealias Index = UInt
typealias Vertices = List<VoronoiGraph.Node.Vertex>
typealias Nodes = List<VoronoiGraph.Node>
inline operator fun <E> MutableList<E>.get(index: Index): E = this[index.toInt()]

data class VoronoiGraph internal constructor(
    val nodes: Nodes,
    val vertices: Vertices = listOf()
) {
    class Node internal constructor(
        val index: Index,
        val origin: IPoint,
        neighboursProvider: NeighboursProvider
    ) {
        val neighbours: List<Node> by neighboursProvider
        val vertices: Vertices = listOf()

        class Vertex internal constructor(
            val index: Index,
            vertexNodesProvider: VertexNodesProvider
            ) {
            
            val nodes: List<Node> by vertexNodesProvider

        }

        override fun equals(other: Any?) = (other as? Node)?.index == index
        override fun hashCode() = index.toInt()
        override fun toString() = "Node(index: $index, origin: $origin)"

    }

    fun findNodeFromOrigin(origin: IPoint): Node? {
        return nodes.find { it.origin == origin }
    }

    fun getNodeFromOrigin(origin: IPoint): Node {
        return findNodeFromOrigin(origin)
            ?: error("fail to find node from $origin")
    }
}

