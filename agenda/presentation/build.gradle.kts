plugins {
    alias(libs.plugins.tasky.android.feature.ui)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.nolawiworkineh.agenda.presentation"
}

dependencies {
    implementation(projects.agenda.domain)
    implementation(projects.core.domain)
    implementation(libs.coil.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
}