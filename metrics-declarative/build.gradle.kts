plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
    id("io.freefair.aspectj.post-compile-weaving") version "9.1.0"
}
dependencies {
    implementation(project(":metrics"))
    implementation(libs.metrics.core)
    implementation(libs.slf4j.api)
    implementation(libs.aspectjrt)
    implementation(libs.aspectjweaver)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.bundles.testing)
    testImplementation(libs.logback.classic)
}
