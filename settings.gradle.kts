pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }

}

rootProject.name = "delaunator-kt"

when (System.getenv("VERSION")?.isNotBlank()) {
    true -> include( "common-ui")
    else -> include( "common-ui",  "javafx", "jetpack-compose")
}


