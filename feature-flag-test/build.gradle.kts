plugins {
    id("buildlogic.java-library-conventions")
}

dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(project(":feature-flag"))
    implementation(libs.slf4j.api)
    implementation(libs.guava)

    implementation(libs.bundles.logback)
    implementation(libs.bundles.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
