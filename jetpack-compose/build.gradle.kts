import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    alias(libs.plugins.compose)
}


repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":common-ui"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"

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
