plugins {
    id("buildlogic.java-library-conventions")
}


dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    //api(libs.commons.math3)
    api(project(":ff"))
    api(project(":ff-ddb"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)
    implementation(libs.aws.sdk2.ddb)

    // Immutables
    compileOnly(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    testImplementation(project(":ff-test"))
    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    testImplementation(project(":database-test"))
    testImplementation(libs.aws.dynamodblocal)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
