import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.allopen") version "1.4.32"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
//    kotlin("kapt") version "1.4.32"
}

group = "cbx.hub"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

subprojects {
    apply{
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.boot:spring-boot-configuration-processor")
        implementation("org.springframework.kafka:spring-kafka")
        developmentOnly("org.springframework.boot:spring-boot-devtools")

        implementation("org.springframework.boot:spring-boot-starter-cache")
        implementation("com.github.ben-manes.caffeine:caffeine")
//        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
//        kapt("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test"){
//            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
//            exclude(module = "mockito-core")
        }
//        testImplementation("org.junit.jupiter:junit-jupiter-api")
//        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
//        testImplementation("com.ninja-squad:springmockk:3.0.1")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}