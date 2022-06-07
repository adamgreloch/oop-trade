import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  id("com.github.johnrengelman.shadow") version "7.1.2"
  java
}

group = "pl.edu.mimuw"
version = "2022"

repositories {
  mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
  named<Test>("test") {
    useJUnitPlatform()
  }

  named<ShadowJar>("shadowJar") {
    mergeServiceFiles()
    manifest {
      attributes(mapOf("Main-Class" to "pl.edu.mimuw.Main"))
    }
  }
}
