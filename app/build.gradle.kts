plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.gb.advanced2"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Dependencies.KTX_CORE)
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation(project(mapOf("path" to ":retrofit")))
    implementation(project(mapOf("path" to ":retrofit")))
    implementation(project(mapOf("path" to ":retrofit")))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Kotlin
    implementation("androidx.fragment:fragment-ktx:1.4.1")

    // Retrofit
    implementation(project(":retrofit"))

    // Koin
    implementation("io.insert-koin:koin-core:3.1.5")
    implementation("io.insert-koin:koin-android:3.1.5")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Room
    implementation(Dependencies.ROOM_RUNTIME)
    kapt(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.ROOM_KTX) // optional, coroutines

    // Coil
    implementation("io.coil-kt:coil:2.0.0-rc02")

    // utils
    implementation(project(":utils"))

    implementation("androidx.core:core-splashscreen:1.0.0-beta02")
}