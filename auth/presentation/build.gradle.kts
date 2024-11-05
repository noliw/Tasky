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
    // Di
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.dagger.hilt.android.compiler)
    testImplementation(libs.dagger.hilt.android.testing)
}