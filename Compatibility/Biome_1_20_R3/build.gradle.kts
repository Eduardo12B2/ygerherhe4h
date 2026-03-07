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
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation(project(":"))
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.cjcrafter:mechanicscore:3.4.1")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
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
