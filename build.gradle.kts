plugins {
    kotlin("jvm") version "1.9.0"

    jacoco
    `java-library`

    `maven-publish`
}

group = "nl.mmathijs"
version = "0.1.4"

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
    finalizedBy(tasks.jacocoTestReport)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

task("printVersionName") {
    println(project.version)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = false
        csv.required = true
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}

jacoco {
    toolVersion = "0.8.9"
}

