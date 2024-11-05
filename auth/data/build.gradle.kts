plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.retrofit)
    alias(libs.plugins.hilt.android.gradle)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.nolawiworkineh.auth.data"
}

dependencies {
    implementation(libs.bundles.dagger)
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
}