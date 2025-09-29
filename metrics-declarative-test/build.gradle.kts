plugins {
    id("buildlogic.java-library-conventions")
    id("io.freefair.aspectj.post-compile-weaving") version "9.0.0"
}
dependencies {
    implementation(project(":metrics"))
    implementation(project(":metrics-declarative"))
    aspect(project(":metrics-declarative"))
    implementation(libs.metrics.core)
    implementation(libs.slf4j.api)
    implementation(libs.aspectjrt)
    implementation(libs.aspectjweaver)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.bundles.testing)
    testImplementation(libs.logback.classic)
}
