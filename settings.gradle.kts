pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }

}

rootProject.name = "delaunator-kt"
include( "common-ui",  "javafx", "jetpack-compose")

