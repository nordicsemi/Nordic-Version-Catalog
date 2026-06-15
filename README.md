# Nordic Version Catalog and BOM

This project provides a Gradle [Version Catalog](https://docs.gradle.org/current/userguide/version_catalogs.html) 
and a [BOM](https://docs.gradle.org/current/userguide/platforms.html) (Bill of Materials) for 
Nordic Semiconductor's libraries.

## Nordic Version Catalog

The Version Catalog is the recommended way to manage dependencies in your Android or 
Kotlin Multiplatform project.

### Usage

Add the following section to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    // [...]
    repositories {
        // [...]
        mavenCentral()
    }
    versionCatalogs {
        create("nordic") {
            from("no.nordicsemi.gradle:nordic-version-catalog:<VERSION>")
        }
    }
}
```

Then, use it in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation(nordic.mcumgr.ble)
    implementation(nordic.blek.client.android)
    // [...]
}
```

## Nordic BOM

The Nordic BOM (Bill of Materials) allows you to manage versions of Nordic libraries by specifying 
only the BOM version, ensuring compatibility between different libraries.

### Usage

Add the BOM to your `build.gradle.kts` and then add the libraries you need without specifying 
their versions:

```kotlin
dependencies {
    implementation(platform("no.nordicsemi.gradle:nordic-bom:<VERSION>"))

    // Now you can add Nordic libraries without versions:
    implementation("no.nordicsemi.android:mcumgr-ble")
    implementation("no.nordicsemi.kotlin.ble:client-android")
    // [...]
}
```

## Available Libraries

See the full list of libraries and their current versions in [libs.versions.toml](gradle/libs.versions.toml).
