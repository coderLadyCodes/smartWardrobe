plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.smartwardrobe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smartwardrobe"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.fragment:fragment:1.6.1")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.3")
    implementation("com.airbnb.android:lottie:6.1.0")
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation("androidx.camera:camera-core:1.4.0-alpha01")
    implementation("androidx.camera:camera-camera2:1.4.0-alpha01")
    implementation ("androidx.camera:camera-lifecycle:1.4.0-alpha01")
    implementation("androidx.camera:camera-view:1.4.0-alpha01")
    implementation ("androidx.camera:camera-extensions:1.4.0-alpha01")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("commons-io:commons-io:2.11.0")
}