
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    id("maven-publish")
}

val projectVersion = System.getenv("VERSION")?.takeIf { it.isNotBlank() }
    ?: "1.0.0"
val nativeOnly = project.hasProperty("nativeOnly")

allprojects {
    group = "io.ygdrasil"
    version = projectVersion
}

repositories {
    mavenCentral()
}

kotlin {

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }

    js(IR)
    macosArm64()
    macosX64()
    linuxX64()
    linuxArm64()
    mingwX64()

    publishing {
        repositories {
            maven {
                name = "GitLab"
                url = uri("https://gitlab.com/api/v4/projects/25805863/packages/maven")
                credentials(HttpHeaderCredentials::class) {
                    name = "Deploy-Token"
                    value = System.getenv("TOKEN")
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
}