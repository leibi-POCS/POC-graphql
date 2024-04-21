plugins {
}

group = "net.leibi"
version = "0.0.1-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {

}

tasks.withType<Test> {
    useJUnitPlatform()
}

