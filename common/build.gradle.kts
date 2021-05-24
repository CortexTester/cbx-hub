

repositories {
    mavenCentral()
}
dependencies {
//    implementation("org.springframework.kafka:spring-kafka")
    implementation("commons-codec:commons-codec")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}
