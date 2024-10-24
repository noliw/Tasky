plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.retrofit)

}

android {
    namespace = "com.nolawiworkineh.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.dagger)
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.database)
    implementation(projects.core.domain)
}