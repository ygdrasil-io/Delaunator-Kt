plugins {
    kotlin("multiplatform") version "1.7.0"
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

    val target = mutableListOf<org.jetbrains.kotlin.gradle.plugin.KotlinTarget>()

    if (!nativeOnly) {
        jvm {
            compilations.all {
                kotlinOptions.jvmTarget = "11"
            }
            testRuns["test"].executionTask.configure {
                useJUnit()
            }
        }.apply { target.add(this) }

        js(IR) {
            browser {
                testTask {
                    useKarma {
                        useChromeHeadless()
                        webpackConfig.cssSupport.enabled = true
                    }
                }
            }
        }.apply { target.add(this) }

    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    when {
        hostOs == "Mac OS X" -> {
            macosX64()
        }
        hostOs == "Linux" -> {
            linuxX64()
        }
        isMingwX64 -> {
            mingwX64()
        }
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }.apply { target.add(this) }

    val publicationsFromMainHost = target.map { it.name }

    publishing {
        repositories {
            maven {
                name = "GitLab"
                url = uri("https://gitlab.com/api/v4/projects/25805863/packages/maven/snapshot")
                credentials(HttpHeaderCredentials::class) {
                    name = "Deploy-Token"
                    value = System.getenv("TOKEN")
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
        publications {
            matching { it.name in publicationsFromMainHost }.all {
                val targetPublication = this@all
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == targetPublication }
                    .configureEach {}
            }
        }
    }
}