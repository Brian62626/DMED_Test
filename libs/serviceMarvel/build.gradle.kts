plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 31
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    testImplementation("junit:junit:4.+")
}
