plugins {
    id("java")
    id("jacoco")
}

group = "com.github.gabrielsilper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }

    classDirectories.setFrom(
        files(
            classDirectories.files.map { directory ->
                fileTree(directory) {
                    exclude(
                        "**/dto/**",
                        "**/model/**",
                        "**/Main.class",
                        "**/FormatterUtils.class",
                    )
                }
            }
        )
    )
}