plugins {
    id("buildlogic.java-library-conventions")
}


dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    //api(libs.commons.math3)
    api(project(":feature-flag"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)

    testImplementation(project(":feature-flag-test"))
    testImplementation(project(":feature-flag-sql"))
    testImplementation(project(":feature-flag-ddb"))
    testImplementation(project(":feature-flag-metrics"))
    testImplementation(platform(libs.aws.sdk2.bom))
    testImplementation("software.amazon.awssdk:dynamodb")
    testImplementation(libs.jdbi.core)
    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
