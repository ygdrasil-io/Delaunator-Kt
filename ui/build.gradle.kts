plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.0"
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("application")
}

javafx {
    version = "14"
    modules = mutableListOf("javafx.controls", "javafx.graphics", "javafx.web")
}

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://jitpack.io") }
}

application {
    mainClassName = "io.ygdrasil.delaunator.ui.FXAppKt"
}

dependencies {
    implementation(project(":delaunator"))
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}