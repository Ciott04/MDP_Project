plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

javafx {
    version = "25" // o la versione JavaFX compatibile con il tuo JDK
    modules = listOf("javafx.controls")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("it.unicam.cs.mpgc.rpg125671.Main")
}

tasks.test {
    useJUnitPlatform()
}