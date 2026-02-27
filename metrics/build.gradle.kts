

plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.publish-conventions")
}
dependencies {
    implementation(libs.slf4j.api)

    testImplementation(libs.bundles.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}