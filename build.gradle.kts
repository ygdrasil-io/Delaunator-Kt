
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
}

val projectVersion = System.getenv("VERSION")?.takeIf { it.isNotBlank() }
    ?: "1.0.0"

allprojects {
    group = "io.ygdrasil"
    version = projectVersion


    repositories {
        mavenCentral()
    }
}

kotlin {
    jvm()
}