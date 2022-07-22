import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    // TODO: switch to stable release when available
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev750"
}


repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":common-ui"))
    implementation(rootProject)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"

    }
}

compose.desktop {
    application {
        mainClass = "io.ygdrasil.delaunator.ui.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "untitled"
        }
    }
}
