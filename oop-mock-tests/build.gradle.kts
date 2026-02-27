
plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.publish-conventions")
}

dependencies {
    implementation(project(":oop-mock-common"))
    implementation(project(":oop-mock-client"))
    implementation(project(":oop-mock"))
    implementation(project(":oop-mock-common"))
    implementation(libs.slf4j.api)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.datatype.jdk8)
    implementation(libs.commons.io)

    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
    implementation(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value )

    implementation(libs.bundles.testing)
    implementation(libs.bundles.logback)
    testImplementation(libs.bundles.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}