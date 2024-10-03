pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Tasky"
include(":app")
include(":core:data")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":core:domain")
include(":core:database")
include(":core:network")
include(":agenda:data")
include(":agenda:presentation")
include(":agenda:domain")
