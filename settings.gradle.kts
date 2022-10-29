pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }

}

rootProject.name = "delaunator-kt"

if (System.getenv("VERSION")?.isBlank() != false) {
    include( "common-ui", "jetpack-compose", "korge")
}


