plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.deeplearning4j:deeplearning4j-core:1.0.0-M2.1")
    implementation("org.nd4j:nd4j-native-platform:1.0.0-M2.1")

    // Elasticsearch Java Client
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.17.0")

    //     F:\software\logstash-8.17.0\bin\logstash.bat F:\software\logstash-8.17.0\config\logstash.conf
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("org.slf4j:slf4j-api:2.0.9") // SLF4J API
    implementation("ch.qos.logback:logback-classic:1.4.11") // Logback implementation

}

tasks.test {
    useJUnitPlatform()
}