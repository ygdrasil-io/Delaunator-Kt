
plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

kotlin {

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

    js {
        browser()
        nodejs()
    }
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