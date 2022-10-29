![Java CI with Gradle](https://github.com/ygdrasil-io/Delaunator-Kt/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)

# Delaunator Kt

Fast [Delaunay triangulation](https://en.wikipedia.org/wiki/Delaunay_triangulation) of 2D points implemented in Kotlin.

This code was ported from [Delaunator C# project](https://github.com/nol1fe/delaunator-sharp) (C#) which is a port from [Mapbox's Delaunator project](https://github.com/mapbox/delaunator) (JavaScript).
<p float="left" align="middle">
<img src="https://raw.githubusercontent.com/ygdrasil-io/Delaunator-Kt/master/images/poisson-disk-sample.png" height="400" width="410">
<img src="https://raw.githubusercontent.com/ygdrasil-io/Delaunator-Kt/master/images/jitter-sample.png" height="400" width="410">
</p>


## Documentation

See https://mapbox.github.io/delaunator/ for more information about the `Triangles` and `Halfedges` data structures.

## Run Jetpack Compose Desktop application
    gradlew :jetpack-compose:run

## Add to Gradle project

First declare the repository on your buildscript

```kotlin
repositories {
    maven {
        url = uri("https://gitlab.com/api/v4/projects/25805863/packages/maven")
    }
}
```

Then add the required dependency regarding your project
```kotlin
dependencies {

    // On MPP Project
    implementation("io.ygdrasil:delaunator-kt:2022.07.01")
    
    // On JS Project
    implementation("io.ygdrasil:delaunator-kt-js:2022.07.01")
}
```
