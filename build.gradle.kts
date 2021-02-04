import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm") version "1.4.30"
    kotlin("kapt") version "1.4.30"

    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"

    id("org.springframework.boot") version "2.4.2"
}

repositories {
    jcenter()
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    kapt("org.springframework.boot:spring-boot-autoconfigure-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.google.guava:guava:30.1-jre")
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

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
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
