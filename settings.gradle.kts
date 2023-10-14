pluginManagement {
    buildscript {
        repositories {
            google()
            gradlePluginPortal()

            mavenCentral()
            maven {
                url = uri("https://storage.googleapis.com/r8-releases/raw")
            }
        }
        dependencies {
            classpath("com.android.tools:r8:8.2.24")
        }
    }
}



dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Yape App"
include(":app")
include(":data")
include(":domain")
include(":cache")
include(":ui")
include(":remote")
