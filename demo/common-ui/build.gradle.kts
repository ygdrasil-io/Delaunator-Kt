import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
}

repositories {
    mavenCentral()
}

kotlin {

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":delaunator"))
            }
        }
    }
}