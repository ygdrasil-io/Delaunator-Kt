
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.kotest)
    id("maven-publish")
}

//System.getProperties().list(System.out)

kotlin {

    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    js(IR) {
        browser()
    }
    macosArm64()
    macosX64()
    linuxX64()
    // Not yet supported by kotest
    //linuxArm64()
    mingwX64()


    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.kotest)
            }
        }
    }

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

