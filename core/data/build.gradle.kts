plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.retrofit)
    alias(libs.plugins.hilt.android.gradle)
    alias(libs.plugins.ksp)

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
    // Di
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.dagger.hilt.android.compiler)
    testImplementation(libs.dagger.hilt.android.testing)

    implementation(libs.androidx.security.crypto.ktx)
}