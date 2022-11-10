package io.ygdrasil.delaunator.voronoi

import io.ygdrasil.delaunator.Delaunator
import io.ygdrasil.delaunator.domain.IEdge
import io.ygdrasil.delaunator.domain.IPoint
import io.ygdrasil.delaunator.indexOfOrNull



fun <T : IPoint> Delaunator<T>.toVoronoiGraph(): VoronoiGraph {
    return VoronoiGraphStructure().apply {
        origins.addAll(points)
        initNodeVertices()
        initNeighbours()
        linkNeighbours(getEdges())

        val seen = HashSet<Int>()  // of point ids
        for (triangleIndex in triangles.indices) {
            val cellIndex = triangles[nextHalfedgeIndex(triangleIndex)]
            if (!seen.contains(cellIndex)) {
                seen.add(cellIndex)
                val vertices = edgesAroundPoint(triangleIndex)
                    .map(::triangleOfEdge)
                    .map(::getTriangleCenter)
                    .map(::findVertexIndexOrCreateVertexAndReturnNewIndex)
                    .onEach(::insertNeighboursNodesToVertex)
                    .toList()
                verticesByNode[cellIndex].addAll(vertices)
            }
        }

    }.getGraph()


    // TODO: inject this
    // vertex: adjacent vertices
    vertices.v[t] = trianglesAdjacentToTriangle(t)

    // vertex: adjacent cells
    vertices.c[t] = pointsOfTriangle(t)
}

context(Delaunator<T>)
private fun <T : IPoint> VoronoiGraphStructure.insertNeighboursNodesToVertex(index: Int) {
    neighboursVertexByVertex[index] = pointsOfTriangle(index)
}

private fun VoronoiGraphStructure.createVertexAndReturnPosition(position: IPoint): Int {
    verticesPosition.add(position)
    neighboursNodesByVertex.add(mutableListOf())
    neighboursVertexByVertex.add(mutableListOf())
    nodesByVertex.add(mutableListOf())

    if (verticesPosition.size != verticesPosition.size) error("verticesPosition size and nodesByVertex should match")
    return verticesPosition.indexOfOrNull(position) ?: error("fail to find vertex index")
}

private fun VoronoiGraphStructure.findVertexIndexOrCreateVertexAndReturnNewIndex(position: IPoint): Int {
    return verticesPosition.indexOfOrNull(position)
        ?: createVertexAndReturnPosition(position)
}

private fun VoronoiGraphStructure.initNodeVertices() =
    repeat(origins.size) { verticesByNode.add(mutableListOf())}

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