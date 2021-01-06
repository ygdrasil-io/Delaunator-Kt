import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(rootProject)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
