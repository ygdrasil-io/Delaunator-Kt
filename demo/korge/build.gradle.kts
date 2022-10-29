import com.soywiz.korge.gradle.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "delaunator.demo"
	jvmMainClassName = "io.ygdrasil.delaunator.ui.MainKt"

	targetJvm()
	targetJs()

	serializationJson()

	gkotlin.apply {

		sourceSets {

			val commonMain by getting {
				dependencies {

					implementation(project(":demo:common-ui"))

				}
			}

		}

	}
}

