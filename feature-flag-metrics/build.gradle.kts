plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}


dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(project(":feature-flag"))
    api(project(":metrics"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)
    implementation(libs.bundles.jdbi)
    implementation(libs.micrometer.core)
    implementation(libs.metrics.core)

    // Immutables
    compileOnly(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    testImplementation(project(":feature-flag-test"))
    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    testImplementation(project(":metrics-test"))

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
