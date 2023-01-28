import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val commonVersion: String by project
val iocVersion: String by project

plugins {
    kotlin("jvm")
    java
    `java-library`
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.jetbrains.dokka") version "1.6.21"
    id("io.github.Kotlin-DI.plugin") version "0.0.2"
}

group = "com.github.kotlin_di"
version = "0.0.2"

kotlin_di {
    keysFile.set("Serialization")
    pluginFile.set("SerializationPlugin")
}

ksp {
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
    implementation("com.github.Kotlin-DI:ioc:$iocVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    include(implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"))
    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "17"
        }
        dependsOn("ktlintFormat")
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
