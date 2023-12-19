plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
}

group = "nl.mmathijs"
version = "0.0.1"

repositories {
    mavenCentral()

    maven(
        url = "https://maven.brott.dev/"
    )
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.acmerobotics.roadrunner:core:0.5.6")
}


tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/gamermathijs/rabid-rider")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}