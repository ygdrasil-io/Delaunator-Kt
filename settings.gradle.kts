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
    include( "delaunator", ":demo:common-ui", ":demo:jetpack-compose", ":demo:korge")
} else {
    include( "delaunator")
}


