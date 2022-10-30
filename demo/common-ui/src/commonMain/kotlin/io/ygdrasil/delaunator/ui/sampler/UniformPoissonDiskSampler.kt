package io.ygdrasil.delaunator.ui.sampler

import io.ygdrasil.delaunator.domain.Point
import kotlin.math.*
import kotlin.random.Random

object UniformPoissonDiskSampler {

    private const val DefaultPointsPerIteration = 30
    private val SquareRootTwo = sqrt(2.0)
    private const val TwoPi = (PI * 2)

    class Settings(
        val TopLeft: Point, val LowerRight: Point, val Center: Point,
        val Dimensions: Point,
        val RejectionSqDistance: Double?,
        val MinimumDistance: Double,
        val CellSize: Double,
        var GridWidth: Int = 0, var GridHeight: Int = 0
    )

    class State(
        val Grid: Array<Array<Point?>>,
        val ActivePoints:MutableList<Point>,
        val Points:ArrayList<Point>
    )

    fun sampleCircle(
        center: Point,
        radius: Double,
        minimumDistance: Double
    ): ArrayList<Point> {
        return sampleCircle(
            center,
            radius,
            minimumDistance,
            DefaultPointsPerIteration
        )
    }

    private fun sampleCircle(
        center: Point,
        radius: Double,
        minimumDistance: Double,
        pointsPerIteration: Int
    ): ArrayList<Point> {
        return sample(
            center - Point(
                radius,
                radius
            ),
            center + Point(radius, radius),
            radius,
            minimumDistance,
            pointsPerIteration
        )
    }


    private fun sample(topLeft: Point, lowerRight: Point, rejectionDistance: Double?, minimumDistance: Double, pointsPerIteration: Int): ArrayList<Point> {
        val settings = Settings(
            TopLeft = topLeft,
            LowerRight = lowerRight,
            Center = (topLeft + lowerRight) / 2,
            Dimensions = lowerRight - topLeft,
            RejectionSqDistance = if (rejectionDistance == null) null else rejectionDistance * rejectionDistance,
            MinimumDistance = minimumDistance,
            CellSize = minimumDistance / SquareRootTwo
        )
        settings.GridWidth = (settings.Dimensions.x / settings.CellSize).toInt() + 1
        settings.GridHeight = (settings.Dimensions.y / settings.CellSize).toInt() + 1

        val state = State(
            Grid = Array(settings.GridWidth) { Array(settings.GridHeight) { null } },
            ActivePoints = mutableListOf(),
            Points = ArrayList()
        )

        addFirstPoint(settings, state)

        while (state.ActivePoints.size != 0) {
            val listIndex = Random.nextInt(state.ActivePoints.size)

            val point = state.ActivePoints[listIndex]
            var found = false

            for (k in 0 until pointsPerIteration) {
                found = found || addNextPoint(
                    point,
                    settings,
                    state
                )
            }

            if (!found) {
                state.ActivePoints.removeAt(listIndex)
            }
        }

        return state.Points
    }

    private fun addFirstPoint(settings: Settings, state: State) {
        var added = false
        while (!added) {
            var d = Random.nextDouble()
            val xr = settings.TopLeft.x + settings.Dimensions.x * d

            d = Random.nextDouble()
            val yr = settings.TopLeft.y + settings.Dimensions.y * d

            val p = Point(xr, yr)
            if (settings.RejectionSqDistance != null && distanceSquared(
                    settings.Center,
                    p
                ) > settings.RejectionSqDistance)
                continue
            added = true

            val index = denormalize(
                p,
                settings.TopLeft,
                settings.CellSize
            )

            state.Grid[index.x.toInt()][index.y.toInt()] = p

            state.ActivePoints.add(p)
            state.Points.add(p)
        }
    }

    private fun addNextPoint(point: Point, settings: Settings, state: State): Boolean {
        var found = false
        val q = generateRandomAround(
            point,
            settings.MinimumDistance
        )

        if (q.x >= settings.TopLeft.x && q.x < settings.LowerRight.x &&
            q.y > settings.TopLeft.y && q.y < settings.LowerRight.y &&
            (settings.RejectionSqDistance == null || distanceSquared(
                settings.Center,
                q
            ) <= settings.RejectionSqDistance)) {
            val qIndex = denormalize(
                q,
                settings.TopLeft,
                settings.CellSize
            )
            var tooClose = false

            for (i in max(0, qIndex.x.toInt() - 2) until min(settings.GridWidth, qIndex.x.toInt() + 3)) {
                for (j in max(0, qIndex.y.toInt() - 2) until min(settings.GridHeight, qIndex.y.toInt() + 3)) {
                    if (tooClose) break
                    tooClose = state.Grid.getOrNull(i)?.getOrNull(j)?.let { value -> distance(
                        value,
                        q
                    ) < settings.MinimumDistance } ?: false
                }
            }

            if (!tooClose) {
                found = true
                state.ActivePoints.add(q)
                state.Points.add(q)
                state.Grid[qIndex.x.toInt()][qIndex.y.toInt()] = q
            }
        }
        return found
    }


    private fun distance(value1: Point, value2: Point): Double {
        val num1 = value1.x - value2.x
        val num2 = value1.y - value2.y
        val num4 = num1 * num1
        val num6 = num2 * num2
        return sqrt(num4 + num6)
    }

    private fun distanceSquared(value1: Point, value2: Point): Double {
        val num1 = value1.x - value2.x
        val num2 = value1.y - value2.y
        val num4 = num1 * num1
        val num6 = num2 * num2
        return num4 + num6
    }

    private fun denormalize(point: Point, origin: Point, cellSize: Double): Point {
        return Point(
            (point.x - origin.x) / cellSize,
            (point.y - origin.y) / cellSize
        )
    }

    private fun generateRandomAround(center: Point, minimumDistance: Double): Point {
            var d = Random.nextDouble()
            val radius = minimumDistance + minimumDistance * d

            d = Random.nextDouble()
            val angle = TwoPi * d

            val newX = radius * sin(angle)
            val newY = radius * cos(angle)

            return Point(center.x + newX, center.y + newY)
        }

}