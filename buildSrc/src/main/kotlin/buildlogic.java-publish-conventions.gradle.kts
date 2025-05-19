
import org.jreleaser.model.Active
import org.jreleaser.model.Signing
plugins {
    // Apply the java Plugin to add support for Java.
    `maven-publish`
    signing
    id("org.jreleaser")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name = "The Codehead Libraries - " + project.name
                description = "Libraries from the CodeHead projects " + project.description
                url = "https://github.com/wolpert/libraries"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "wolpert"
                        name = "Ned Wolpert"
                        email = "ned.wolpert@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/wolpert/libraries.git"
                    developerConnection = "scm:git:ssh://github.com/wolpert/libraries.git"
                    url = "https://github.com/wolpert/libraries/"
                }
            }

        }
    }
    repositories {
        maven {
            setUrl(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

jreleaser {
    gitRootSearch = true
    strict = false
    signing {
        active = Active.ALWAYS
        armored = true
    }
    release {
        github {
            enabled = false
        }
    }

    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    username = providers.gradleProperty("sonatypeUsername")
                    password = providers.gradleProperty("sonatypePassword")

                    applyMavenCentralRules = true
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    //stagingRepositories = listOf("build/staging-deploy")
                    stagingRepository("target/staging-deploy")
                }
            }
        }
    }
//    deploy {
//        maven {
//            mavenCentral {
//                create("sonatype") {
//                    active = Active.ALWAYS
//                    url = "https://central.sonatype.com/api/v1/publisher"
//                    stagingRepository("build/staging-deploy")
//                }
//            }
//        }
//    }
}

// V1
//jreleaser {
//    signing {
//        active = Active.ALWAYS
//        armored = true
//        mode = Signing.Mode.MEMORY
//
//        passphrase = providers.gradleProperty("signingPassphrase")
//        secretKey = providers.gradleProperty("signingSecretKey")
//        publicKey = providers.gradleProperty("signingPublicKey")
//    }
//
//    deploy {
//        maven {
//            mavenCentral {
//                create("sonatype") {
//                    username = providers.gradleProperty("mavenCentralUsername")
//                    password = providers.gradleProperty("mavenCentralPassword")
//
//                    active = Active.ALWAYS
//                    url = "https://central.sonatype.com/api/v1/publisher"
//                    stagingRepositories = listOf("build/staging-deploy")
//                }
//            }
//        }
//    }
//}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}
