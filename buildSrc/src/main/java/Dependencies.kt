import org.gradle.api.JavaVersion

object Config {
    const val appId = "com.esfimus.gbtranslator"
    const val compileSdk = 33
    const val minSdk = 26
    const val targetSdk = 33
    val javaVersion = JavaVersion.VERSION_19
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"

    const val searchHistoryScreen = ":searchHistoryScreen"
}

object Versions {
    // Design
    const val appcompat = "1.6.1"
    const val material = "1.9.0"
    const val swipeLayout = "1.1.0"

    // Kotlin
    const val core = "1.10.1"

    // Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val interceptor = "5.0.0-alpha.11"
    const val coroutinesAdapter = "0.9.2"

    // Koin
    const val koinCore = "3.4.3"
    const val koinAndroid = "3.4.3"

    // Coil
    const val coil = "2.4.0"
    const val coilSvg = "2.4.0"

    // Room
    const val roomRuntime = "2.5.2"
    const val roomCompiler = "2.5.2"
    const val roomKtx = "2.5.2"

    // Test
    const val junit = "4.13.2"
    const val extJunit = "1.1.5"
    const val espressoCore = "3.5.1"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swipeLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeLayout}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
    const val coroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapter}"
}

object Koin {
    const val koinCore = "io.insert-koin:koin-core:${Versions.koinCore}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koinAndroid}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilSvg = "io.coil-kt:coil-svg:${Versions.coilSvg}"
}

object Room {
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.junit}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}