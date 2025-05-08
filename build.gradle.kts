/*
 * Copyright 2025 Humberto Gomes, José Lopes, Mariana Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Project configuration

plugins {
    application
    jacoco
    id("info.solidsoft.pitest") version "1.15.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.1")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "MakeItFit.Main"
}

jacoco {
    toolVersion = "0.8.13"
}

// Test configuration

var testTarget = "unittests"
if (project.hasProperty("testDir")) {
    testTarget = project.property("testDir").toString()
}

java.sourceSets["test"].java {
    srcDir("src/${testTarget}/java")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

// Other tasks

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.register<Exec>("format") {
    workingDir = file(rootDir)
    commandLine = listOf("sh", "-c", "find src -type f | xargs -n1 sh -c 'clang-format -i $0; sed -i s/\\\\r//g $0'")
}

// PIT Mutation Testing
pitest {
    junit5PluginVersion.set("1.2.2")
    pitestVersion.set("1.19.1")

    targetClasses.set(setOf("MakeItFit.*"))
    testSourceSets.set(listOf(sourceSets.test.get()))
    mainSourceSets.set(listOf(sourceSets.main.get()))

    outputFormats.set(setOf("HTML"))
    timestampedReports.set(false)
}
