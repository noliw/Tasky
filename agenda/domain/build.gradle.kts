plugins {
    alias(libs.plugins.tasky.jvm.library)
}


dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.domain)

}