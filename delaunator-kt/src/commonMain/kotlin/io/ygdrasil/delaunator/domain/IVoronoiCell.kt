package io.ygdrasil.delaunator.domain

interface IVoronoiCell {

    val points: List<IPoint>
    val index: Int
}