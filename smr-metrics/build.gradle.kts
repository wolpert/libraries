
plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}

project.description = "Metrics integratino for the simplified state machine"

dependencies {

    implementation(project(":smr"))
    implementation(project(":metrics"))
    implementation(libs.slf4j.api)

    testImplementation(project(":metrics-test"))
    testImplementation(libs.bundles.testing)
    testImplementation(libs.bundles.logback)

}
