plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}

dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(project(":ff"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)
    implementation(libs.bundles.jdbi)

    // Immutables
    compileOnly(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    testImplementation(project(":ff-test"))
    testImplementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    //testImplementation(libs.database.test)

    testImplementation(libs.liquibase.core)
    testImplementation(libs.hsqldb)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
