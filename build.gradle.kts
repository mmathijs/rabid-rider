plugins {
    kotlin("jvm") version "1.9.0"
    application
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

application {
    mainClass.set("MainKt")
}