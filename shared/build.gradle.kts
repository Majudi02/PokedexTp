plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.squareup.sqldelight")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {


        val ktorVersion = "2.3.1"
        val sqldelightVersion = "1.5.5"

        val commonMain by getting{
            dependencies{
                //Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                //Napier
                implementation("io.github.aakira:napier:2.6.1")

                //Serialization
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                //SqlDeLight
                implementation("com.squareup.sqldelight:runtime:$sqldelightVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting{
            dependencies{
                //Ktor
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

                //SqlDeLight
                implementation("com.squareup.sqldelight:android-driver:$sqldelightVersion")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies{
                //Ktor
                implementation("io.ktor:ktor-client-ios:2.3.1")

                //SqlDeLight
                implementation("com.squareup.sqldelight:native-driver:$sqldelightVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.tptallerdedisep"
    compileSdk = 33
    defaultConfig {
        minSdk = 24

    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.pokedex.db"
    }
}



