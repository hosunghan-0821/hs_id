// plugins are applied from root build.gradle.kts

dependencies {
    // Core 모듈 의존성 (Domain 클래스들을 사용하기 위해)
    implementation(project(":auth-core"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.33")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // Spring Security
    api("org.springframework.boot:spring-boot-starter-security")

    // HTTP Client for OAuth2 external API calls
    implementation("org.springframework.boot:spring-boot-starter-web")

}

tasks.test {
    useJUnitPlatform()
}
