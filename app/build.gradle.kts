plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
   // id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.netology.tco2rus"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.netology.tco2rus"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("com.yandex.android:maps.mobile:4.5.1-full")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    kapt ("com.github.bumptech.glide:compiler:4.16.0")
    implementation("androidx.preference:preference-ktx:1.2.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.transport.api)
    implementation(libs.androidx.swiperefreshlayout)


    val core_version = "1.16.0"
    val appcompat_version = "1.7.0"
    val mdc_version = "1.12.0"
    val constraintlayout_version = "2.2.0"
    val recyclerview_version = "1.4.0"
    val junit_version = "4.13.2"
    val ext_junit_version = "1.2.1"
    val espresso_core_version = "3.6.1"
    val activity_version = "1.10.1"
    val lifecycle_version = "2.9.0"
    val gson_version = "2.11.0"
    val nav_version = "2.9.0"
    val room_version = "2.7.1"
    val firebase_version = "33.13.0"
    val play_services_base_version = "18.7.0"
    val okhttp_version = "4.12.0"
    val glide_version = "4.16.0"
    val retrofit_version = "2.11.0"
    val retrofitgson_version = "2.11.0"
    val okhttplogging_version = "4.12.0"
    val coroutines_version = "1.10.2"
    val imagepicker_version = "2.1"
    val hilt_version = "2.56.2"
    val paging_version = "3.3.6"
    val core_desugaring_version = "2.1.5"

    coreLibraryDesugaring(libs.desugar.jdk.libs)


    implementation ("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("com.github.dhaval2404:imagepicker:$imagepicker_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttplogging_version")
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitgson_version")
    implementation("androidx.core:core-ktx:$core_version")
    implementation("androidx.appcompat:appcompat:$appcompat_version")
    implementation("com.google.android.material:material:$mdc_version")
    implementation("androidx.constraintlayout:constraintlayout:$constraintlayout_version")
    implementation("androidx.recyclerview:recyclerview:$recyclerview_version")
    implementation("androidx.activity:activity-ktx:$activity_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("com.google.code.gson:gson:$gson_version")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
  //  implementation(platform("com.google.firebase:firebase-bom:$firebase_version"))
   // implementation("com.google.firebase:firebase-messaging-ktx")
   // implementation("com.google.android.gms:play-services-base:$play_services_base_version")
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation("com.github.bumptech.glide:glide:$glide_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("androidx.room:room-paging:$room_version")



    testImplementation("junit:junit:$junit_version")
    androidTestImplementation("androidx.test.ext:junit:$ext_junit_version")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso_core_version")
}