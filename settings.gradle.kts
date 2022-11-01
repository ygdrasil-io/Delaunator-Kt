pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }

}

rootProject.name = "delaunator kotlin"

include( ":delaunator", ":demo:common-ui", ":demo:jetpack-compose", ":demo:korge")