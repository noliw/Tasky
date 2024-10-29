plugins {
    alias(libs.plugins.tasky.android.feature.ui)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android.gradle)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.nolawiworkineh.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
}