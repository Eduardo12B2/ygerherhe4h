plugins {
    java
    id("io.papermc.paperweight.userdev")
}

repositories {
    mavenCentral()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    // Paper 1.21.11 dev bundle
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    implementation(project(":"))

    compileOnly("com.cjcrafter:mechanicscore:4.2.5")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.4.0")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val reobf = configurations.maybeCreate("reobf")
reobf.isCanBeConsumed = true
reobf.isCanBeResolved = false

artifacts {
    add("reobf", tasks.reobfJar)
}
