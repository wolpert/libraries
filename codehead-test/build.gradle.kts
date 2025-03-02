description = "General testing utilities"

plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}


dependencies {

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    //api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.jsr305)
    implementation(libs.slf4j.api)
    implementation(libs.bundles.jackson)
    implementation(libs.bundles.testing)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.datatype.jdk8)
    annotationProcessor(libs.immutables.value)

    testImplementation(libs.immutables.annotations)
    testImplementation(libs.jackson.annotations)
    testAnnotationProcessor(libs.immutables.value)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
