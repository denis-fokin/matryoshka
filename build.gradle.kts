plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("maven-publish")
    id("com.jfrog.bintray") version "1.8.4"
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

val publicationName = "Lib" // could by any

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            from(project.components["java"])
        }
    }

    repositories {
        // TODO: target repo for publish otherwise only publishToMavenLocal
    }
}

bintray {
    user = when {
        project.hasProperty("bintrayUser") -> project.property("bintrayUser").toString()
        else -> System.getenv("BINTRAY_USER")
    }

    key = when {
        project.hasProperty("bintrayApiKey") -> project.property("bintrayApiKey").toString()
        else -> System.getenv("BINTRAY_API_KEY")
    }

    publish = true

    pkg.apply {
        repo = "" // TODO: Provide target repo name

        vcsUrl = "https://github.com/denis-fokin/matreshka"
        setLicenses("Apache-2.0")

        name = "matreshka"
        version.apply {
            name = "0.0.0" //TODO: pass from CI or somewhere else
        }
    }

    setPublications(publicationName)
}
