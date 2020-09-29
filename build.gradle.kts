import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.System.getenv

plugins {
	kotlin("jvm") version "1.3.72"
	`maven-publish`
}

group = "com.archesky.apollo.library"
version = "0.0.${getenv().getOrDefault("GITHUB_RUN_ID", "1")}-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.apollographql.apollo:apollo-runtime:1.4.4")
        implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:7.1.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Rich43/archesky-apollo-library")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
}
