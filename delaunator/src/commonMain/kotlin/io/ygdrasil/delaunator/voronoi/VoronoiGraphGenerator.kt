package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.Delaunator
import io.ygdrasil.delaunator.domain.IEdge
import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.indexOfOrNull

private fun <V> Map<Int, V>.toStructuredList(): List<V> {
    return keys.sorted().map { getOrElse(it) { error("unreachable statement") } }
}

fun <T : IPoint> Delaunator<T>.toVoronoiGraph(): VoronoiGraph {
    val verticesByNode = mutableMapOf<Int, List<Int>>()
    val neighboursNodesByNode = mutableMapOf<Int, List<Int>>()
    val verticesPosition = mutableMapOf<Int, IPoint>()
    val neighboursNodesByVertex = mutableMapOf<Int, List<Int>>()
    val neighboursVertexByVertex = mutableMapOf<Int, List<Int>>()

    for (triangleId in triangles.indices) {

        val vertex = triangles[nextHalfedgeIndex(triangleId)]
        if (!verticesByNode.containsKey(vertex)) {
            val edges = edgesAroundPoint(triangleId)

            verticesByNode[vertex] = edges.map { edge -> triangleOfEdge(edge) }.toList()
            neighboursNodesByNode[vertex] = edges.map { edge -> triangles[edge] }.toList()

        }

        val triangle = triangleOfEdge(triangleId)
        if (!verticesPosition.containsKey(triangle)) {

            verticesPosition[triangle] = getTriangleCenter(triangle)
            neighboursVertexByVertex[triangle] = trianglesAdjacentToTriangle(triangle)
            neighboursNodesByVertex[triangle] = pointsOfTriangle(triangle)
        }
    }

    return VoronoiGraphStructure(
        points,
        verticesByNode.toStructuredList(),
        neighboursNodesByNode.toStructuredList(),
        verticesPosition.toStructuredList(),
        neighboursVertexByVertex.toStructuredList(),
        neighboursNodesByVertex.toStructuredList()
    ).getGraph()
}

