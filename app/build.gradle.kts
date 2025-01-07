import com.android.build.gradle.BaseExtension

plugins {
   // alias(libs.plugins.androidApplication)
    //alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.android.application") // Apply the Android Gradle Plugin
    id("org.jetbrains.kotlin.android") // Apply the Kotlin Android Plugin
    id("jacoco")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}
tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

android {
    namespace = "com.kirabium.relayance"
    compileSdk = 35

    testCoverage {
        version = "0.8.8"
    }

    defaultConfig {
        applicationId = "com.kirabium.relayance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //testInstrumentationRunner = "runner.CucumberTestRunner"
        testInstrumentationRunner = "io.cucumber.android.runner.CucumberAndroidJUnitRunner"


        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    sourceSets.all {
        println("Source set: ${name} -> ${java.srcDirs}")
    }

    sourceSets {
        getByName("androidTest") {
            java.srcDirs("src/androidTest/kotlin")
            resources.srcDirs("src/androidTest/resources")
        }
    }


    testOptions {
        unitTests.all {
            // Add the following line to make the task untracked
            it.doNotTrackState("")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val androidExtension = extensions.getByType<BaseExtension>()

val jacocoTestReport by tasks.registering(JacocoReport::class) {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")
    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug")
    val mainSrc = androidExtension.sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(debugTree)
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(fileTree(buildDir) {
        include("**/*.exec", "**/*.ec")
    })
}

dependencies {

    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.espresso.intents)
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.cucumber.android)
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0") // Updated to latest stable version

    // JUnit (for unit tests)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0") // Updated to latest stable version
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0") // Updated to latest stable version

    // Cucumber
    implementation("io.cucumber:cucumber-java:7.20.1") // For Cucumber Java support
    androidTestImplementation("io.cucumber:cucumber-android:7.14.0") // Cucumber Android integration
    androidTestImplementation("io.cucumber:cucumber-junit:7.14.0")
    androidTestImplementation("io.cucumber:cucumber-junit-rules-support:7.14.0")

    // AndroidX Test (for instrumentation tests)
    androidTestImplementation("androidx.test:runner:1.6.1")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    androidTestImplementation("androidx.test:rules:1.6.1")

    // JUnit 4 (required for Cucumber)
    androidTestImplementation("junit:junit:4.13.2")

    // Hilt (Dependency Injection)
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.espresso.contrib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.register("cucumber") {
    dependsOn("assembleDebug", "compileDebugAndroidTestSources")
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = configurations.getByName("androidTestRuntimeClasspath").incoming.artifactView {}.files +
                    fileTree("src/androidTest/kotlin") +
                    fileTree("src/androidTest/resources")
            args = listOf(
                "--plugin", "pretty",
                "--glue", "steps",
                "src/androidTest/resources/features"
            )
        }
    }
}

tasks.withType<Test> {
    doNotTrackState("Disabling state tracking for test tasks")
}

tasks.register("printConfigurations") {
    doLast {
        configurations.forEach { configuration ->
            println("Configuration: ${configuration.name} (canBeResolved: ${configuration.isCanBeResolved})")
        }
    }
}