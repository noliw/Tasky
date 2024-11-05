plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.retrofit)
    alias(libs.plugins.tasky.android.room)
}

android {
    namespace = "com.nolawiworkineh.core.database"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.dagger)
    implementation(projects.core.domain)
    implementation(libs.org.mongodb.bson)
}