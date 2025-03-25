plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}


dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    //api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)
    implementation(libs.guava) // hashing
    implementation(libs.bundles.caffine)
    implementation(libs.bundles.jdbi)

    // Immutables
    compileOnly(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    testImplementation(libs.pgjdbc)
    testImplementation(libs.testcontainers)
    testImplementation(libs.testcontainers.postgres)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
