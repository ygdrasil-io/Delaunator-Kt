import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.jvm)
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":delaunator-kt"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
