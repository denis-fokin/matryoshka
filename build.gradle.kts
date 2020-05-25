plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("maven-publish")
}

group = "com.ogogon.swing.dsl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation(group = "junit", name = "junit", version = "4.12")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
