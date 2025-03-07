
plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}

project.description = "Simplified state machine"

dependencies {

    implementation(libs.jsr305)
    implementation(libs.slf4j.api)
    implementation(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    testImplementation(libs.bundles.testing)
    testImplementation(libs.bundles.logback)
}
