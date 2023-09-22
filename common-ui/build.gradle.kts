import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":delaunator-kt"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
