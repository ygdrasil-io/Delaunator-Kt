import com.soywiz.korge.gradle.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "com.sample.demo"


	targetJvm()
	targetJs()

	serializationJson()
}

