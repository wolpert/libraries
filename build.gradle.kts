
allprojects {
    group = "com.codeheadsystems"
    version = "3.1.0-SNAPSHOT"
}
plugins {
    id("nl.littlerobots.version-catalog-update") version "1.0.1"
}

// gradle jreleaserConfig
// gradle clean build test publish jreleaserDeploy
