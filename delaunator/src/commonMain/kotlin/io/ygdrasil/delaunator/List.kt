package io.ygdrasil.delaunator

internal fun <E> List<E>.indexOfOrNull(it: E) =
    indexOf(it).let { if (it == -1) null else it }