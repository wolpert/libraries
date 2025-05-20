
import org.jreleaser.model.Active
import org.jreleaser.model.Signing
plugins {
    // Apply the java Plugin to add support for Java.
    `java-library`
    `maven-publish`
    signing
    id("org.jreleaser")
}

java {
    withJavadocJar()
    withSourcesJar()
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

val sonatypeUsername = providers.gradleProperty("sonatypeUsername")
val sonatypePassword = providers.gradleProperty("sonatypePassword")

jreleaser {
    gitRootSearch = true
    strict = false
    signing {
        active = Active.ALWAYS
        armored = true
    }
    release {
        github {
            enabled = true
            repoOwner = "wolpert"
            repoUrl = "https://github.com/wolpert/library"
            skipRelease = true
            skipTag = true
            sign = true
            overwrite = true
        }
    }

    deploy {
        maven {
            mavenCentral {
                register("sonatype") {
                    username = sonatypeUsername
                    password = sonatypePassword

                    applyMavenCentralRules = true
                    active = Active.RELEASE
                    url = "https://central.sonatype.com/api/v1/publisher"
                    //stagingRepositories = listOf("build/libs")
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}
