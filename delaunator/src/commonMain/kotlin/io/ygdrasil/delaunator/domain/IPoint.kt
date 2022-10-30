package io.ygdrasil.delaunator.domain

interface IPoint {
    var x: Double
    var y: Double

    operator fun <K, V> Map.Entry<K, V>.component1() = x
    operator fun <K, V> Map.Entry<K, V>.component2() = y
}