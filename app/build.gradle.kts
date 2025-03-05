plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.mostafa.whatnow"
    compileSdk = 35
    buildFeatures{
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.mostafa.whatnow"
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // retrofit library
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // gson convertor
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // glide library to display pictures
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
}