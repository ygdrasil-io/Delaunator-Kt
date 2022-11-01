package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.Delaunator
import io.ygdrasil.delaunator.domain.IEdge
import io.ygdrasil.delaunator.domain.IPoint

fun <T : IPoint> Delaunator<T>.toVoronoiGraph(): VoronoiGraph {
    return VoronoiGraphStructure().apply {
        origins.addAll(points)
        initNeighbours()
        linkNeighbours(getEdges())

    }.getGraph()

}

private fun VoronoiGraphStructure.linkNeighbours(edges: Sequence<IEdge>) = edges
    .map { edge -> edge.toNodeIndexes(origins) }
        .forEach(::linkNode)

private fun IEdge.toNodeIndexes(origins: List<IPoint>): Pair<Int, Int> {
    return origins.indexOf(q) to origins.indexOf(p)
}

private fun VoronoiGraphStructure.initNeighbours() =
    repeat(origins.size) { neighbours.add(mutableListOf()) }

private fun VoronoiGraphStructure.linkNode(edge: Pair<Int, Int>) = edge.let { (left, right) ->
    linkNode(left, right)
    linkNode(right, left)
}

private fun VoronoiGraphStructure.linkNode(left: Int, right: Int) {
    if (neighbours[left].contains(right).not()) {
        neighbours[left].add(right)
    }
}