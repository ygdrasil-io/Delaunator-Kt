plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.4.0"
}

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