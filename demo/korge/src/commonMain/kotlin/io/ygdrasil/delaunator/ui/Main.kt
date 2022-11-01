package io.ygdrasil.delaunator.ui

import com.soywiz.korge.Korge
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.sceneContainer
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors

const val canvasSize = 800
const val buttonHeight = 60

suspend fun main() =
    Korge(width = canvasSize, height = canvasSize, bgcolor = Colors.BLACK, title = "Delaunator Demo") {
        val sceneContainer = sceneContainer()

        sceneContainer.changeTo({ MyScene() })
    }

class MyScene : Scene() {

    private lateinit var container: FixedSizeContainer
    val applicationState = ApplicationState(canvasSize)

    override suspend fun SContainer.sceneMain() {
        container = fixedSizeContainer(canvasSize.toDouble(), canvasSize - buttonHeight.toDouble(), true)
        redraw()

        uiButton(
            "generate poisson sample",
            width = canvasSize / 2.0,
            height = buttonHeight.toDouble()
        ) {
            position(.0, canvasSize - height)
            onClick {
                applicationState.regenerate(ApplicationState.Sampler.PoisonDisc)
                redraw()
            }
            enable()
        }

        uiButton(
            "generate jitter sample",
            width = canvasSize / 2.0,
            height = buttonHeight.toDouble()
        ) {
            position(width, canvasSize - height)
            onClick {
                applicationState.regenerate(ApplicationState.Sampler.Jitter)
                redraw()
            }
            enable()
        }
    }

    private fun redraw() {
        container.graphics {
            val drawer = KorgeDrawer(this, container)
            ApplicationDrawer(drawer, applicationState)
                .draw()
        }
    }
}
