package io.ygdrasil.delaunator.voronoi

import kotlin.reflect.KProperty

internal interface NeighboursProvider {
    operator fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): Nodes
}

internal interface NodeByVertexProvider {
    operator fun getValue(thisRef: VoronoiGraph.Node.Vertex, property: KProperty<*>): Nodes
}

internal interface VerticesProvider {
    operator fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): Vertices
}