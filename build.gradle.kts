import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20"
    kotlin("kapt") version "1.4.20"

    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"

    id("org.springframework.boot") version "2.4.0"
}

repositories {
    jcenter()
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.google.guava:guava:30.0-jre")
    implementation("io.micrometer:micrometer-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.4.2")
    implementation("org.springframework.boot:spring-boot-actuator")
    implementation("org.springframework.boot:spring-boot-actuator-autoconfigure")
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation(platform("org.junit:junit-bom:5.+"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-test")
    testImplementation("org.assertj:assertj-core")
}

kotlin {
    explicitApi()
}

tasks.test {
    useJUnitPlatform()
}

gradle.taskGraph.whenReady {
    val isRelease = hasTask(":release")
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            allWarningsAsErrors = isRelease
        }
    }
}
