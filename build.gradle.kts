import com.android.build.gradle.internal.lint.AndroidLintTask
import org.gradle.internal.jvm.Jvm

buildscript {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
//        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.3.0")
    }
}

plugins {
    id("com.google.devtools.ksp") version "1.8.0-1.0.8" apply false
}

println("Gradle uses Java ${Jvm.current()}")

subprojects {
    tasks.withType<AndroidLintTask>().all { enabled = false }
    tasks.withType<Javadoc>().all { enabled = false }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

}



