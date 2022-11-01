package io.ygdrasil.delaunator.voronoi

import kotlin.reflect.KProperty

internal interface NeighboursProvider {
    operator fun getValue(thisRef: VoronoiGraph.Node, property: KProperty<*>): List<VoronoiGraph.Node>
}

internal interface VertexNodesProvider {
    operator fun getValue(thisRef: VoronoiGraph.Node.Vertex, property: KProperty<*>): List<VoronoiGraph.Node>
}