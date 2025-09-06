
allprojects {
    group = "com.codeheadsystems"
    version = "3.0.5"
    //version = "3.0.3-SNAPSHOT"
}
plugins {
    id("nl.littlerobots.version-catalog-update") version "1.0.0"
}

// gradle jreleaserConfig
// gradle clean build test publish jreleaserDeploy
