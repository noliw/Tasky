plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.retrofit)
}

android {
    namespace = "com.nolawiworkineh.agenda.data"
}

dependencies {
    implementation(projects.agenda.domain)
    implementation(projects.core.domain)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.work)
    implementation(libs.kotlinx.serialization.json)
}