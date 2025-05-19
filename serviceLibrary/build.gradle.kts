//  import info.git.versionHelper.getVersionText
import info.git.versionHelper.runCommand

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("com.google.devtools.ksp")
}

val versionText = "git rev-list --count HEAD".runCommand(project.rootDir)

android {
    namespace = "info.mqtt.android.service"
    testNamespace = "info.mqtt.android.service.test"
    compileSdk = 34
    defaultConfig {
        minSdk = 21

        // Android Studio 4.1 doesn"t generate versionName in libraries any more
        // https://developer.android.com/studio/releases/gradle-plugin#version_properties_removed_from_buildconfig_class_in_library_projects
        buildConfigField("String", "VERSION_NAME", "\"${versionText}\"")

        testApplicationId = "info.mgtt.android.service.test"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments.putAll(
            mapOf(
                "useTestStorageService" to "true",
            ),
        )

        packagingOptions {
            pickFirsts += setOf("META-INF/serviceLibrary_release.kotlin_module")
        }

//        packaging {
//            resources {
//                pickFirsts += setOf("META-INF/serviceLibrary_debug.kotlin_module")
//            }
//        }

//        ksp {
//            arg("room.schemaLocation", "$projectDir/schemas")
//        }

        buildTypes {
            release {
                isMinifyEnabled = false
                consumerProguardFile("proguard-sdk.pro")
            }
        }

//        buildFeatures {
//            buildConfig = true
//        }
    }

//    testOptions {
////        targetSdk = 34
//    }

//    testFixtures {
//        enable = true
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    publishing {
        singleVariant("release") {}
    }
    configurations.all {
        resolutionStrategy {
            force("androidx.lifecycle:lifecycle-livedata:2.5.1")
            force("androidx.lifecycle:lifecycle-livedata-core:2.5.1")
            force("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.8.0")
            force(" org.jetbrains:annotations:13.0")
        }
    }
}

dependencies {
    api("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    implementation("com.github.AppDevNext.Logcat:LogcatCoreLib:3.3.1")

    implementation("androidx.room:room-runtime:2.4.3")
    ksp("androidx.room:room-compiler:2.4.3")

    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.2")
    androidTestUtil("androidx.test.services:test-services:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.test:rules:1.6.1")
    testImplementation(kotlin("test"))
}
