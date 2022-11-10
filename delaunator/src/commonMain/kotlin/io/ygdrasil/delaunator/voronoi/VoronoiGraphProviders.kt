package io.ygdrasil.delaunator.voronoi

import kotlin.reflect.KProperty

internal interface NeighboursProvider {
    operator fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): Nodes
}

internal interface NeighboursNodesOfVertexProvider {
    operator fun getValue(vertex: VoronoiGraph.Node.Vertex, property: KProperty<*>): Nodes
}

internal interface NeighboursVerticesOfVertexProvider {
    operator fun getValue(vertex: VoronoiGraph.Node.Vertex, property: KProperty<*>): Vertices
}

internal interface VerticesProvider {
    operator fun getValue(node: VoronoiGraph.Node, property: KProperty<*>): Vertices
}

