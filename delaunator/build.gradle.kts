plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.4.0"
    `maven-publish`
}

group = "com.github.chaosnya"
version = "1.0.0"

repositories {
    mavenCentral()
    jcenter()
}


kotlin {
    macosX64()
    linuxX64()
    mingwX64()
    jvm()
}