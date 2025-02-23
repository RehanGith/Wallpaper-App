plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger)
    kotlin("kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.my_wallpapers"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.my_wallpapers"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
}

dependencies {
    //dependencies for using dagger hilt
    implementation(libs.dagger.hilt)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    kapt(libs.dagger.kapt)
    //dependencies for retrofits
    implementation(libs.android.retrofit.json)
    implementation(libs.android.retrofit2)
    //for loading images
    implementation(libs.github.glide)
    //navigation and viewModle
    implementation(libs.androidx.lifecyle.viewmodel)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}