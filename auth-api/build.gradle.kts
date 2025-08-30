// plugins are applied from root build.gradle.kts

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")


    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
    
    // Project Dependencies
    implementation(project(":auth-core"))
    implementation(project(":auth-infrastructure"))


}

tasks.test {
    useJUnitPlatform()
}