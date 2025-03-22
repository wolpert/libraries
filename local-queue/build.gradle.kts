
plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-publish-conventions")
}

dependencies {
    implementation (libs.guava) // for hashing
    implementation(libs.dropwizard4.core)
    api(libs.micrometer.core)
    implementation(libs.slf4j.api)
    // SQL
    implementation(libs.jdbi.core)
    implementation(libs.jdbi.testing)
    implementation(libs.jdbi.sqlobject)

    implementation(libs.javax.inject)
    implementation(libs.dagger)
    annotationProcessor(libs.bundles.dagger.annotation)
    implementation(libs.immutables.annotations)
    annotationProcessor(libs.immutables.value)

    implementation(project(":metrics"))
    implementation(project(":metrics-micrometer"))

    implementation(libs.liquibase.core)


    testImplementation(libs.bundles.logback)
    testImplementation(libs.hsqldb)
    testImplementation(libs.c3p0)
    testImplementation(libs.bundles.testing)
    testImplementation(project(":codehead-test"))
    testImplementation(project(":metrics-test"))
    testAnnotationProcessor(libs.bundles.dagger.annotation)
    testAnnotationProcessor(libs.immutables.value)
}

