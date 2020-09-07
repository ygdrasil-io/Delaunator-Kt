[![](https://jitpack.io/v/chaosnya/Delaunator-Kt.svg)](https://jitpack.io/#chaosnya/Delaunator-Kt)
[![](https://jitci.com/gh/chaosnya/Delaunator-Kt/svg)](https://jitci.com/gh/chaosnya/Delaunator-Kt)

# Delaunator Kt

Fast [Delaunay triangulation](https://en.wikipedia.org/wiki/Delaunay_triangulation) of 2D points implemented in Kotlin.

This code was ported from [Delaunator C# project](https://github.com/nol1fe/delaunator-sharp) (C#) which is a port from [Mapbox's Delaunator project](https://github.com/mapbox/delaunator) (JavaScript).
<p float="left" align="middle">
<img src="https://github.com/chaosnya/Delaunator-Kt/blob/master/images/poisson-disk-sample.png" height="400" width="410">
<img src="https://github.com/chaosnya/Delaunator-Kt/blob/master/images/jitter-sample.png" height="400" width="410">
</p>


## Documentation

See https://mapbox.github.io/delaunator/ for more information about the `Triangles` and `Halfedges` data structures.

## Run JavaFX application

    gradlew run
    
    
## Usage
### Gradle
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
    
    allprojects {
        repositories {
    		...
            maven { url 'https://jitpack.io' }
        }
    }
    	
Step 2. Add the dependency
    
    dependencies {
   	    implementation 'com.github.chaosnya:Delaunator-Kt:v1.0.0'
    }
    
### Maven

Step 1. Add the JitPack repository to your build file
    
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
    	
Step 2. Add the dependency
    
	<dependency>
	    <groupId>com.github.chaosnya</groupId>
	    <artifactId>Delaunator-Kt</artifactId>
	    <version>v1.0.0</version>
	</dependency>
