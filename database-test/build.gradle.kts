// gradle clean build test publishToSonatype closeAndReleaseSonatypeStagingRepository

description = "Database testing utilities for DynamoDB"

plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}

dependencies {
    implementation(platform(libs.aws.sdk2.bom))
    implementation("software.amazon.awssdk:dynamodb")
    implementation(project(":codehead-test"))
    implementation(libs.aws.dynamodblocal)
    implementation(libs.aws.sdk2.ddb)
    implementation(libs.slf4j.api)
    api(libs.bundles.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.bundles.logback)
}