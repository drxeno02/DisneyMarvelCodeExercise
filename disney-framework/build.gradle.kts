plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.disney.framework"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        // there are two buildTypes {debug, release}. The only difference between debug
        // and release buildTypes is that code debug logging is enabled in debug environment,
        // otherwise logging is disabled.
        debug {
            buildConfigField("boolean", "DEBUG_MODE", "true")
        }
        release {
            buildConfigField("boolean", "DEBUG_MODE", "false")
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
        buildConfig = true
    }
}

dependencies {
    // androidx
//    implementation("androidx.core:core-ktx:1.12.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.9.0")

    // androidx work
//    def work = '2.6.0'
    testImplementation("androidx.work:work-testing:2.6.0")
    // 2.7.1 for work-runtime-ktx is required to avoid crash on Android 12 API 31
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    // core testing
//    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // gson
    implementation("com.google.code.gson:gson:2.9.0")

    // okHttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    // testing & debugging
    val jupiter = "5.8.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$jupiter")
    testImplementation("org.junit.vintage:junit-vintage-engine:$jupiter")
    val mokito = "4.6.1"
    testImplementation("org.mockito:mockito-core:$mokito")
    testImplementation("org.mockito:mockito-inline:$mokito")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.9")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
