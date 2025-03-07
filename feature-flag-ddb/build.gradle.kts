plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}


dependencies {

    api(project(":feature-flag"))

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.slf4j.api)
    implementation(platform(libs.aws.sdk2.bom))
    implementation("software.amazon.awssdk:dynamodb")

    // Immutables
    compileOnly(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    testImplementation(libs.bundles.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
