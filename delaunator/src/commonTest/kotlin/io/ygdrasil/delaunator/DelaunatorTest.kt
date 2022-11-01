package io.ygdrasil.delaunator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.ygdrasil.delaunator.domain.Point
import io.ygdrasil.delaunator.voronoi.voronoiGraphKtTest

class DelaunatorTest : StringSpec({

    (0 until 3).forEach {
        "delaunator should throw error when not enough point when using $it point(s)" {
            shouldThrow<Throwable> {
                (0..it).map { Point(.0, .0) }
                    .toDelaunator()
            }
        }
    }

    voronoiGraphKtTest()

})