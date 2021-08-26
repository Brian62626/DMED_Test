buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.1.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}

tasks.register<Delete>("clean") {
    this.delete.add(rootProject.buildDir)
}

subprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}