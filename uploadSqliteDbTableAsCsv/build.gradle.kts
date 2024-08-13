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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
/*publishing {
    publications {
        create<MavenPublication>("mavenRelease") {
            afterEvaluate {
                from(components.findByName("release"))
            }
            groupId = "com.github.saeedjavazanjan"
            artifactId = "uploadSqliteDbTableAsCsv"
            version = "1.0.0"
        }

    }

}*/

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.saeedjavazanjan"
            artifactId = "uploadSqliteDbTableAsCsv"
            version = "1.0.0"
            afterEvaluate {
                from(components.findByName("release"))
            }
            }
        }
    }

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
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

