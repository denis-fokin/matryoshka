plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("maven-publish")
    signing
}


group = "com.ogogon.swing.dsl"
version = "0.0.3-SNAPSHOT"

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

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            credentials {
                username = "fds"
                password = "Pfrfxfnm111!"
            }
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

signing {
    sign(publishing.publications["default"])
}
