import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":delaunator"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
