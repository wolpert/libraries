plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.publish-conventions")
}
dependencies {
    implementation(project(":metrics"))
    implementation(libs.metrics.core)
    implementation(libs.slf4j.api)
    implementation(libs.micrometer.core)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.bundles.testing)
    testImplementation(libs.logback.classic)
}
