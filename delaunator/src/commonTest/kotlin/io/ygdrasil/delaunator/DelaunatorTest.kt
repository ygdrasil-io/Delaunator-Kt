package io.ygdrasil.delaunator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DelaunatorTest : StringSpec({

    (0 until 3).forEach {
        "delaunator should throw error when not enough point when using $it point(s)" {
            shouldThrow<Throwable> {
                (0..it).map { Point(.0, .0) }
                    .toDelaunator()
            }
        }
    }

    "falling test" {
        0 shouldBe 1
    }

})