plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    //id("org.graalvm.buildtools.native") version "0.9.28"
    id("com.google.protobuf") version "0.9.4"
}

group = "net.leibi"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")


    //implementation("io.grpc:grpc-protobuf:1.63.0")
    //implementation("io.grpc:grpc-stub:1.63.0")

    implementation("com.google.protobuf:protobuf-java:3.24.0")
    implementation("com.google.protobuf:protobuf-java-util:3.24.0")


    implementation("net.devh:grpc-spring-boot-starter:3.1.0.RELEASE")
    runtimeOnly("io.grpc:grpc-netty-shaded:1.63.0")

    // runtimeOnly("com.google.protobuf:protobuf-gradle-plugin:0.8.13")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}



protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.0"
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}


