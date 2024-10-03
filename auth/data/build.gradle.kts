plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.retrofit)
}

android {
    namespace = "com.nolawiworkineh.auth.data"
}

dependencies {
    implementation(libs.bundles.dagger)
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}