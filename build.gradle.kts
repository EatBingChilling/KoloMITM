plugins {
    `maven-publish`
    id("java-library")
    id("application")
    id("com.gradleup.shadow") version "9.0.0-rc1"
    kotlin("jvm") version "2.2.0"
}

group = "io.github.eatbingchilling.kolomitm"
version = "1.0-SNAPSHOT"

application {
    applicationName = "KoloMITM"
    mainClass = "io.github.mucute.qwq.kolomitm.KoloMITM"
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "io.github.mucute.qwq.kolomitm.KoloMITM")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/maven-snapshots")
    maven("https://repo.opencollab.dev/maven-releases")
}

/* ---------- 发布配置 ---------- */
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = "KoloMITM"
            version = project.version.toString()

            pom {
                packaging = "jar"
                url.set("https://github.com/EatBingChilling/KoloMITM")
                scm {
                    connection.set("scm:git:git://github.com/EatBingChilling/KoloMITM.git")
                    developerConnection.set("scm:git:ssh://github.com/EatBingChilling/KoloMITM.git")
                    url.set("https://github.com/EatBingChilling/KoloMITM")
                }
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer { name.set("EatBingChilling") }
                }
            }
        }
    }

    /* 同时支持本地 & GitHub Packages */
    repositories {
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/EatBingChilling/KoloMITM")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

/* ---------- 依赖 ---------- */
dependencies {
    api(libs.bedrock.codec)
    api(libs.common)
    api(libs.bedrock.connection)
    api(libs.transport.raknet)
    api(libs.kotlinx.coroutines)
    api(libs.net.raphimc.minecraftauth)
    api(libs.jackson.databind)
    api(platform(libs.log4j.bom))
    api(libs.log4j.api)
    api(libs.log4j.core)
    testApi(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
