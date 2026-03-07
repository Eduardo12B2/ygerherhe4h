plugins {
    java
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17" apply false
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // Paper
    maven(url="https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Spigot
    maven(url="https://repo.dmulloy2.net/repository/public/") // ProtocolLib
    maven(url="https://maven.enginehub.org/repo/") // WorldEdit
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.4.0")
    compileOnly("com.cjcrafter:mechanicscore:4.2.5")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.9")

    // Adventure API - provided by Paper 1.21+ natively
    compileOnly("net.kyori:adventure-api:4.17.0")
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.4")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.17.0")
    compileOnly("net.kyori:adventure-text-minimessage:4.17.0")

    implementation("org.bstats:bstats-bukkit:3.0.1")
}

configurations.all {
    resolutionStrategy {
        force("com.google.code.gson:gson:2.11.0")
        force("com.google.guava:guava:32.1.3-jre")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}
