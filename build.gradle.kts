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

// Comamnd-line arguments

var testTarget = "unittests"
if (project.hasProperty("testDir")) {
    testTarget = project.property("testDir").toString()
}

var jvmVersion = JavaVersion.VERSION_21
if (testTarget == "evosuitetests") {
    jvmVersion = JavaVersion.VERSION_1_8
}

if (JavaVersion.current() != jvmVersion) {
    throw GradleException(
        "Wrong java version for this task: use Java 8 for EvoSuite and 21 for everything else"
    )
}

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
    // JUnit 4
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.12.2")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.2")

    // EvoSuite Runtime
    testImplementation("org.evosuite:evosuite-standalone-runtime:1.2.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClass = "MakeItFit.Main"
}

// Test configuration

java.sourceSets["test"].java {
    srcDir("src/${testTarget}/java")
}

tasks.named<Test>("test") {
    useJUnitPlatform {
        includeEngines("junit-jupiter", "junit-vintage")
    }
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.13"
}

pitest {
    junit5PluginVersion.set("1.2.2")
    pitestVersion.set("1.19.1")

    targetClasses.set(setOf("MakeItFit.*"))
    testSourceSets.set(listOf(sourceSets.test.get()))
    mainSourceSets.set(listOf(sourceSets.main.get()))

    outputFormats.set(setOf("HTML"))
    timestampedReports.set(false)
}

// Other tasks

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.register<Exec>("format") {
    workingDir = file(rootDir)
    commandLine = listOf("sh", "-c", "find src -type f | xargs -n1 sh -c 'clang-format -i $0; sed -i s/\\\\r//g $0'")
}
