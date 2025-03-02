
plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}

project.description = "Provides YAML support for the simplified state machine"

dependencies {

    implementation(project(":smr"))
    implementation(libs.jackson.dataformat.yaml)
    implementation(libs.slf4j.api)

    testImplementation(libs.bundles.testing)
    testImplementation(libs.bundles.logback)
}
