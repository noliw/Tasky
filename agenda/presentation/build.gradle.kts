plugins {
    alias(libs.plugins.tasky.android.feature.ui)
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
}