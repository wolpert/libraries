plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}


dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    //api(libs.commons.math3)
    api(project(":feature-flag"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)
    implementation(libs.jetcd)

    testImplementation(project(":feature-flag-test"))
    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    testImplementation(libs.jetcd.test)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
