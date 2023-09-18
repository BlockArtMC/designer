plugins {
    kotlin("jvm") version "1.9.0"
}

group = "net.deechael"
version = properties["designer-version"]!!

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly(files("libs/artist-api-1.20.1-R0.1-SNAPSHOT.jar"))

    implementation("com.zaxxer:HikariCP:5.0.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<ProcessResources>().configureEach {
    inputs.property("version", rootProject.version)
    filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
        expand(
            mapOf(
                Pair("version", rootProject.version)
            )
        )
    }
}