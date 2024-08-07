plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.ksp)
    id("maven-publish")
}

android {
    namespace = "com.saeed.android.uploadSqliteDbTableAsCsv"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}
publishing {
    publications {
        create<MavenPublication>("mavenRelease") {
            afterEvaluate {
                from(components.findByName("release"))
            }
            groupId = "com.uploadDb"
            artifactId = "uploadSqliteDbTableAsCsv"
            version = "1.0.0"
        }
    }

}
dependencies {

    implementation(libs.androidx.appcompat)
    androidTestImplementation(libs.androidx.espresso.core)


    //room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    // To use Kotlin annotation processing tool (kapt)
    ksp(libs.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    //csv
    implementation(libs.opencsv)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //reflection
    implementation(libs.kotlin.reflect)


}

