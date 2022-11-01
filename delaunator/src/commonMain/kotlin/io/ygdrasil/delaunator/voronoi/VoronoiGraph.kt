package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.domain.IPoint

typealias Index = UInt
typealias Vertices = List<VoronoiGraph.Node.Vertex>
typealias Nodes = List<VoronoiGraph.Node>

data class VoronoiGraph internal constructor(
    val nodes: Nodes,
    val vertices: Vertices
) {
    class Node internal constructor(
        val index: Index,
        val origin: IPoint,
        neighboursProvider: NeighboursProvider,
        verticesProvider: VerticesProvider
    ) {
        val neighbours: List<Node> by neighboursProvider
        val vertices: Vertices by verticesProvider

        class Vertex internal constructor(
            val index: Index,
            val position: IPoint,
            nodeByVertexProvider: NodeByVertexProvider
            ) {
            
            val nodes: List<Node> by nodeByVertexProvider

            override fun toString() = "Vertex(index: $index, position: $position)"
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

