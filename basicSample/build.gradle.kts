import info.git.versionHelper.getGitCommitCount
import info.git.versionHelper.getLatestGitHash
import info.git.versionHelper.getVersionText
import info.git.versionHelper.runCommand
import org.jetbrains.kotlin.fir.scopes.impl.overrides

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

val versionText = "git rev-list --count HEAD".runCommand(project.rootDir)

android {
    namespace = "info.mqtt.java.example"
    defaultConfig {
        applicationId = "info.mqtt.java.example"
        minSdk = 21
        compileSdk = 34
        targetSdk = 34
        versionCode = getGitCommitCount()
        versionName = "${versionText}.$versionCode-${getLatestGitHash()}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments.putAll(
            mapOf(
                "useTestStorageService" to "true",
            ),
        )
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    configurations.all {
        resolutionStrategy {
            force("androidx.lifecycle:lifecycle-livedata:2.5.1")
            force("androidx.lifecycle:lifecycle-livedata-core:2.5.1")
            force("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.8.0")
        }
    }
}

dependencies {
    implementation(project(":serviceLibrary"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.github.AppDevNext.Logcat:LogcatCoreLib:3.3.1")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    implementation("androidx.lifecycle:lifecycle-common:2.3.1")
    implementation("com.google.android.material:material:1.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.github.AppDevNext:Moka:1.4") {
        exclude("androidx.lifecycle", "lifecycle-common")
//        overrides()
    }
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.2")
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestUtil("androidx.test.services:test-services:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0") {
        exclude("com.google.android.material", "material")
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}