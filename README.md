# H2O 
A simple water reminder app

## Screenshots
<img src="/screenshots/screenshot1.png" width="200" /> <img src="/screenshots/screenshot2.png" width="200" /> <img src="/screenshots/screenshot3.png" width="200" />

## Installation Guide
- Download or clone this remote repository to your local machine.
- Download and open Android Studio
- Sync the project to download dependencies
- Run the application using an emulator or a real device.

## Main Dependencies
### [Room](https://developer.android.com/topic/libraries/architecture/room)
The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

### [Retrofit](https://github.com/square/retrofit)
A type-safe HTTP client for Android and Java

### [Koin](https://github.com/InsertKoinIO/koin)
Koin is a pragmatic lightweight dependency injection framework for Kotlin developers to whom we will give the responsibility to instantiate the different objects of our application.

### [MockK](https://github.com/mockk/mockk)
In Kotlin, all classes and methods are final. While this helps us write immutable code, it also causes some problems during testing.

Most JVM mock libraries have problems with mocking or stubbing final classes. Of course, we can add the “open” keyword to classes and methods that we want to mock. But changing classes only for mocking some code doesn't feel like the best approach.

Here comes the MockK library, which offers support for Kotlin language features and constructs. MockK builds proxies for mocked classes. This causes some performance degradation, but the overall benefits that MockK gives us are worth it.

### [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously. On Android, coroutines help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive.

### [WaveLoadingView](https://github.com/tangqi92/WaveLoadingView)
An Android library that provides a realistic wave-loading effect.

### [Croller](https://github.com/harjot-oberai/Croller)
Custom seekbar
