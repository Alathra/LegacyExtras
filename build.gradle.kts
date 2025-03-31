import java.time.Instant

plugins {
    `java-library`

    id("com.github.johnrengelman.shadow") version "8.1.1" // Shades and relocates dependencies, See https://imperceptiblethoughts.com/shadow/introduction/
    id("xyz.jpenilla.run-paper") version "2.3.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0" // Automatic plugin.yml generation

    eclipse
    idea
}

group = "me.ShermansWorld"

version = "1.29.4"
description = ""
val mainPackage = "${project.group}.${rootProject.name}"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17)) // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
}

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://jitpack.io/") {
        content {
            includeGroup("com.github.milkdrinkers")
            includeGroup("com.github.darksaid98")
            includeGroup("com.palmergames.bukkit.towny")
            includeGroup("com.github.MilkBowl")
            includeGroup("com.github.TownyAdvanced")
            includeGroup("com.github.Xiao-MoMi")
        }
    }

    maven("https://repo.momirealms.net/releases/")

    maven("https://repo.codemc.org/repository/maven-public/") {
        content { includeGroup("dev.jorel") }
    }

    maven("https://maven.athyrium.eu/releases")

    maven("https://maven.citizensnpcs.co/repo/")

    maven("https://repo.glaremasters.me/repository/towny/") {
        content {
            includeGroup("com.palmergames.bukkit.towny")
            includeGroup("com.github.TownyAdvanced")
        }
    }

    maven("https://nexus.bencodez.com/repository/maven-public/")

    maven("https://repo.essentialsx.net/releases/")
    maven("https://repo.essentialsx.net/snapshots/")

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        content { includeGroup("me.clip") }
    }
    
    maven("https://repo.codemc.org/repository/maven-public/")
    
    maven("https://mvn.lumine.io/repository/maven-public/") {
        content { includeGroup("io.lumine") }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:24.1.0")

    implementation("com.github.milkdrinkers:crate:1.2.1")
    implementation("com.github.milkdrinkers:colorparser:2.0.3")

//    implementation("dev.jorel:commandapi-bukkit-shade:9.0.3")
//    compileOnly("dev.jorel:commandapi-annotations:9.0.3")
//    annotationProcessor("dev.jorel:commandapi-annotations:9.0.3")

    compileOnly("net.citizensnpcs:citizens-main:2.0.35-SNAPSHOT") {
        exclude(group = "*", module = "*")
    }

    compileOnly("com.palmergames.bukkit.towny:towny:0.100.3.12")
    compileOnly("com.bencodez:votingplugin:6.16.3") {
        exclude(group = "*", module = "*")
    }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("net.essentialsx:EssentialsX:2.20.1")
    compileOnly("me.clip:placeholderapi:2.11.5")
    compileOnly("dev.cubxity.plugins:unifiedmetrics-api:0.3.8")
    compileOnly("nl.rutgerkok:blocklocker:1.12.1")
    compileOnly("io.lumine:Mythic-Dist:5.6.2")
    compileOnly(files("lib/SiegeEngines-0.8.4.jar"))
    compileOnly("net.momirealms:custom-fishing:2.3.3")
    compileOnly("com.palmergames.bukkit:TownyChat:0.115")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
        options.compilerArgs.addAll(arrayListOf("-Xlint:all", "-Xlint:-processing", "-Xdiags:verbose"))
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")

        // Shadow classes
        // helper function to relocate a package into our package
        fun reloc(originPkg: String, targetPkg: String) = relocate(originPkg, "${project.group}.${targetPkg}")

        reloc("com.github.milkdrinkers.Crate", "crate")
        reloc("com.github.milkdrinkers.colorparser", "colorparser")

        minimize()
    }

    runServer {
        // Configure the Minecraft version for our task.
        minecraftVersion("1.20.4")

        // IntelliJ IDEA debugger setup: https://docs.papermc.io/paper/dev/debugging#using-a-remote-debugger
        jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-DPaper.IgnoreJavaVersion=true", "-Dcom.mojang.eula.agree=true", "-DIReallyKnowWhatIAmDoingISwear")
        systemProperty("terminal.jline", false)
        systemProperty("terminal.ansi", true)

        // Automatically install dependencies
        downloadPlugins {
            github("MilkBowl", "Vault", "1.7.3", "Vault.jar")
        }
    }
}

bukkit {
    // Plugin main class (required)
    main = "${mainPackage}.${rootProject.name}"

    // Plugin Information
    name = project.name
    prefix = project.name
    version = "${project.version}"
    description = "${project.description}"
    authors = listOf("ShermansWorld")
    contributors = listOf("darksaid98", "NuclearDonut47", "Xavbeat03", "NinjaMandalorian", "AubriTheHuman", "LOUofSPARTA", "rooooose-b")
    apiVersion = "1.19"

    // Misc properties
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.POSTWORLD // STARTUP or POSTWORLD
    depend = listOf("Towny", "TownyChat")
    softDepend = listOf("Essentials", "PlaceholderAPI", "UnifiedMetrics", "CustomFishing")

    commands {
        register("roll") {
            description = "A command used to roll dice."
            usage = "/roll"
        }
        register("freeop") {
            description = "Gives free operator permissions."
            usage = "/freeop"
        }
        register("tutorialbook") {
            description = "Gives the AlathraMC tutorial book."
            usage = "/tutorialbook"
        }
        register("puke") {
            description = "Pukes out random items from the player."
            permission = "AlathraExtras.puke"
            usage = "/puke"
        }
        register("alathraextras") {
            description = "Some random commands."
        }
        register("playtime") {
            description = "Display a player's playtime."
            usage = "/playtime"
        }
        register("showitem") {
            description = "Show an item in chat."
            usage = "/showitem"
        }
        register("yeet") {
            description = "Guess."
            usage = "/yeet <player>"
        }
    }
}

// Apply custom version arg
val versionArg = if (hasProperty("customVersion"))
    (properties["customVersion"] as String).uppercase() // Uppercase version string
else
    "${project.version}-SNAPSHOT-${Instant.now().epochSecond}" // Append snapshot to version

// Strip prefixed "v" from version tag
project.version = if (versionArg.first().equals('v', true))
    versionArg.substring(1)
else
    versionArg.uppercase()
