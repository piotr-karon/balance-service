plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.5.21"
    id("com.google.cloud.tools.jib") version "3.1.4"
}

jib {
    from {
        image = "adoptopenjdk/openjdk16:jre-16.0.1_9-alpine"
    }
    to {
        image = "pugs-local"
        tags = setOf("latest")
    }

    container {
        ports = listOf("8080")
        creationTime = "USE_CURRENT_TIMESTAMP"
        jvmFlags = listOf("-Djdk.tls.ephemeralDHKeySize=2048")
    }
}

dependencies {
    implementation(project(":app:domain"))

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.springframework.security:spring-security-test")

    testImplementation("org.amshove.kluent:kluent:1.68")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
}
