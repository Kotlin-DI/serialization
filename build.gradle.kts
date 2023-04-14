import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val commonVersion: String by project
val processorVersion: String by project
val iocVersion: String by project

plugins {
    kotlin("jvm")
    java
    `java-library`
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    id("org.jetbrains.dokka")
    id("com.github.Kotlin-DI.gradlePlugin") version "0.0.3"
}

group = "com.github.kotlinDI"
version = "0.0.2"

kotlinDI {
    common.set(commonVersion)
    processor.set(processorVersion)
    keysFile.set("Serialization")
    pluginFile.set("SerializationPlugin")
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://jitpack.io")
    mavenLocal()
}

dependencies {
    implementation(kotlin("reflect"))
    dependsOn(implementation("com.github.Kotlin-DI:ioc:$iocVersion"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    include(implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"))
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

ktlint {
    version.set("0.48.2")
    outputToConsole.set(true)
    filter {
        exclude("**/generated/**")
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "17"
        }
    }
    withType<org.jlleitschuh.gradle.ktlint.tasks.KtLintFormatTask> {
        dependsOn(":kspKotlin", ":kspTestKotlin")
    }
    withType<org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask> {
        dependsOn(":kspKotlin", ":kspTestKotlin")
    }
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            from(components["java"])
        }
    }
}