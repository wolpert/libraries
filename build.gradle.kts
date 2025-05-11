// For publishing, all of these are required in the top-level.

plugins {
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}
nexusPublishing {
    repositories {
        sonatype()
    }
}
allprojects {
    group = "com.codeheadsystems"
    version = "3.0.3"
    //version = "3.0.3-SNAPSHOT"
}
// gradle jreleaserConfig
// gradle clean build test jreleaserFullRelease
