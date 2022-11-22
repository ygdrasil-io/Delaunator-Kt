package io.ygdrasil.delaunator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.ygdrasil.delaunator.Point
import io.ygdrasil.delaunator.voronoi.voronoiGraphTest

class DelaunatorTest : StringSpec({

    delaunatorTest()
    voronoiGraphTest()

})

val delaunatorTest: StringSpec.() -> Unit = {

    (0 until 3).forEach {
        "delaunator should throw error when not enough point when using $it point(s)" {
            shouldThrow<Throwable> {
                (0..it).map { Point(.0, .0) }
                    .toDelaunator()
            }
        }
    }

}