import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.kapt")
}

// Memuat file apikey.properties
val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties()

if (apikeyPropertiesFile.exists()) {
    apikeyProperties.load(FileInputStream(apikeyPropertiesFile))
} else {
    throw GradleException("File apikey.properties tidak ditemukan di root project.")
}


android {
    namespace = "id.ac.polbeng.nurchumairoh.githubprofile"
    compileSdk = 35

    defaultConfig {
        applicationId = "id.ac.polbeng.nurchumairoh.githubprofile"
        minSdk = 21
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Menambahkan ACCESS_TOKEN dari apikey.properties ke BuildConfig
        buildConfigField(
            "String",
            "ACCESS_TOKEN",
            apikeyProperties["ACCESS_TOKEN"].toString()
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            debug {
                // Menggunakan token yang sama dari apikey.properties
                // buildConfigField 'String', 'ACCESS_TOKEN', '"your_access_token_here"'
            }
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
        dataBinding = true
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

    // Lottie untuk animasi splash screen
    implementation("com.airbnb.android:lottie:5.2.0")

    // CircleImageView untuk menampilkan gambar bulat
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Glide untuk menampilkan gambar
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Retrofit untuk request API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp Logging Interceptor untuk logging API
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.9")
}