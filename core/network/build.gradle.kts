plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.retrofit)
}

android {
    namespace = "com.nolawiworkineh.core.network"
}

dependencies {
    implementation(projects.core.domain)
}