import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("application")
}

javafx {
    version = "15.0.1"
    modules = mutableListOf("javafx.controls", "javafx.graphics", "javafx.web")
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "io.ygdrasil.delaunator.ui.FXAppKt"
}

dependencies {
    implementation(rootProject)
    implementation(project(":common-ui"))
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}