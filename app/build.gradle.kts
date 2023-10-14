import dependencies.UiDep

plugins {
    id(Config.Plugins.android)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.navigationSafArgs)
    id(Config.Plugins.kotlinKapt)
    id(Config.Plugins.dagger)
}

android {
    namespace = Environments.Release.appId
    compileSdk = Versions.compileAndTargetSdk

    defaultConfig {
        applicationId = Environments.Release.appId
        minSdk = Versions.minSdk
        targetSdk =  Versions.compileAndTargetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = Config.testRunner
        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = Versions.jvmTarget
    }
    buildFeatures {
        dataBinding =true
        viewBinding = true
    }
}

dependencies {

    // Modules
    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.remote))
    implementation(project(Modules.cache))
    implementation(project(Modules.presentation))

    // Core Dependencies
    implementation(UiDep.kotlin)
    implementation(UiDep.coreKtx)
    implementation(UiDep.appCompat)
    implementation(UiDep.material)
    implementation(UiDep.constraint)
    implementation(UiDep.navigationFragmentKtx)
    implementation(UiDep.navigationUiKtx)
    implementation(UiDep.activityKtx)
    // LifeCycle
    UiDep.LifeCycle.forEach {
        implementation(it)
    }
    // Dagger-Hilt
    UiDep.DaggerHilt.forEach {
        implementation(it)
    }
    UiDep.DaggerHiltKapt.forEach {
        kapt(it)
    }
    // Coroutines
    UiDep.Coroutines.forEach {
        implementation(it)
    }
    // Glide
    implementation(UiDep.glide)

    kapt(UiDep.glideKapt)

    // Lottie animation
    implementation(UiDep.lottie)

    // Test Dependencies
    testImplementation(UiDep.Test.junit)
    testImplementation(UiDep.Test.assertJ)
    testImplementation(UiDep.Test.mockitoKotlin)
    testImplementation(UiDep.Test.mockitoInline)
    testImplementation(UiDep.Test.coroutines)
    testImplementation(UiDep.Test.androidxArchCore)
    testImplementation(UiDep.Test.robolectric)
    testImplementation(UiDep.Test.testExtJunit)


    //maps
    implementation(UiDep.googleMaps)
}