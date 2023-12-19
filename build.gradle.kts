plugins {
    kotlin("jvm") version "1.9.0"

    `java-library`
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
    api(kotlin("stdlib-jdk8"))

    testImplementation(kotlin("test"))

    implementation("com.acmerobotics.roadrunner:core:0.5.6")
}

java {
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}