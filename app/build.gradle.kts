plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.mostafa.whatnow"
    compileSdk = 35

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.mostafa.whatnow"
        minSdk = 23
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
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // xml card view additional
    implementation("androidx.cardview:cardview:1.0.0")
    // material icon AppBar
    implementation("com.google.android.material:material:1.9.0")
    // retrofit library
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // gson convertor
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // glide library to display pictures
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // http for resolve api error
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    // Firebase Auth
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore:24.10.3")
}