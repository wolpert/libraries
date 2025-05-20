
import org.jreleaser.model.Active
plugins {
    `maven-publish`
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
    dryrun = false
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
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}
