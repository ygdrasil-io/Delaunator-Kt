
plugins {
    alias(libs.plugins.multiplatform) apply false
}

val projectVersion = System.getenv("VERSION")?.takeIf { it.isNotBlank() }
    ?: "1.0.0"
val nativeOnly = project.hasProperty("nativeOnly")

allprojects {
    group = "io.ygdrasil"
    version = projectVersion
}

repositories {
    mavenCentral()
}