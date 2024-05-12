This is a Kotlin Multiplatform project that was done with the aim of studying the Geolocation API and Worker API from
the Android SDK.

# Description

Collect geolocation in CoroutineWorker every 15 mins and send it to backend.
Explore collected location points on Yandex Maps running inside WebView.

Desktop application don't have location provider. You can use FAB to send mock geolocation point with random values.

* `/composeApp` - Compose Multiplatform module targeting Android and Desktop.
    * WebView
        * Based on AndroidView for Android target
        * Based on interop of JavaFX WebView into Swing which in turn based on interop Swing into Compose

* `/server` - simple backend based on Ktor.

# How to run

1. Change `SERVER_IP` in `Constants.kt` on yours
2. Start server
    - `./gradlew :server:run`
3. Run an application
    - Desktop
        - `./gradlew :composeApp:run`
    - Android (Requires API Level 30 or more)
        - `./gradlew :composeApp:installDebug`