package io.ygdrasil.delaunator.domain

interface IEdge {

    val p: IPoint
    val q: IPoint
    val index: Int

}