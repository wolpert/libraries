plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.publish-conventions")
}
dependencies {
    implementation(project(":metrics"))
    implementation(libs.bundles.testing)
    implementation(libs.metrics.core)
    implementation(libs.slf4j.api)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}