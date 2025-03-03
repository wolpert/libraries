plugins {
    id("buildlogic.java-library-conventions")
}


dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    //api(libs.commons.math3)
    api(project(":ff"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)

    testImplementation(project(":ff-test"))
    testImplementation(project(":ff-sql"))
    testImplementation(project(":ff-ddb"))
    testImplementation(project(":ff-metrics"))
    testImplementation(libs.aws.sdk2.ddb)
    testImplementation(libs.jdbi.core)
    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
