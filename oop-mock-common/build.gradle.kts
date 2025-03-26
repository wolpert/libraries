plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}
dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.datatype.jdk8)
    implementation(libs.commons.io)

    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
    implementation(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value )

    testAnnotationProcessor(libs.immutables.value)
    testImplementation(libs.bundles.testing)
    testImplementation(libs.bundles.logback)
    testAnnotationProcessor(libs.dagger.compiler)
    testImplementation(project(":codehead-test"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
