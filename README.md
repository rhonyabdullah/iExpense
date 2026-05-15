This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

## Architecture

This project follows **Clean Architecture** with **MVI (Model-View-Intent)** in the Presentation layer.

```mermaid
graph TD
    subgraph "Presentation Layer"
        VM[ViewModel] -->|StateFlow| S[State]
        I[Intent] -->|dispatch| VM
        VM -->|SharedFlow| E[Effect]
    end

    subgraph "Domain Layer"
        UC[Use Cases] -->|interfaces| RI[Repository Interfaces]
        DM[Domain Models]
    end

    subgraph "Data Layer"
        RI_Impl[Repository Implementations] -.->|implements| RI
        M[Mappers]
        RM[RemoteMediator / PagingSource]
    end

    subgraph "Storage & Network"
        DB[(Database Layer - Room)]
        NET[Network Layer - Ktor]
    end

    VM -.-> UC
    UC -.-> RI
    RI_Impl --> M
    RI_Impl --> RM
    RI_Impl --> DB
    RI_Impl --> NET
```

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
