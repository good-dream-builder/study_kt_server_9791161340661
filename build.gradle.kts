import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
}

group = "com.songko"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// KOTLIN
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// WEB
	implementation("org.springframework.boot:spring-boot-starter-web")

	// DB
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	runtimeOnly("mysql:mysql-connector-java")

	// jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// Login Token
	// jwt
	implementation("com.auth0:java-jwt:3.8.1")

	// FCM
	implementation("com.google.firebase:firebase-admin:6.8.1")


	// mybatis를 사용할 경우 아래 주석 해제.
	// implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	// implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.2")

	// SECURITY
	// implementation("org.springframework.boot:spring-boot-starter-security")
	// testImplementation("org.springframework.security:spring-security-test")

	// UTILS
	implementation("net.coobird:thumbnailator:0.4.8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	// implementation("org.springframework.boot:spring-boot-starter-hateoas")

	// TEST
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	// HASH
	implementation("org.mindrot:jbcrypt:0.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
