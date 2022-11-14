import io.ygdrasil.delaunator.domain.Point
import io.ygdrasil.delaunator.toDelaunator
import io.ygdrasil.delaunator.voronoi.toVoronoiGraph

val sample = listOf(Point(.0,.0), Point(.0,1.0), Point(1.0,.0), Point(1.0,1.0))

object Sampler {

    fun sample() = mutableListOf<Point>().apply {
        val width = 100
        val height = 100
        val step = 10
        for (x in 0..width step step) {
            for (y in 0 .. height step step) {
                add(Point(x.toDouble(), y.toDouble()))
            }
        }
    }
}

fun main2() {
    val delaunator = Sampler.sample().toDelaunator()
    val voronoiGraph = delaunator.toVoronoiGraph()
    println(voronoiGraph)
}