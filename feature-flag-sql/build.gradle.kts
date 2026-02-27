plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.publish-conventions")
}

dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(project(":feature-flag"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)
    implementation(libs.bundles.jdbi)

    // Immutables
    compileOnly(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    testImplementation(project(":feature-flag-test"))
    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    //testImplementation(libs.database.test)

    testImplementation(libs.liquibase.core)
    testImplementation(libs.hsqldb)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
