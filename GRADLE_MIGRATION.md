# Stroke5 Keyboard Android Project - Gradle Migration

This project has been successfully migrated from Eclipse/Ant to Gradle build system.

## Project Structure

The project now follows the standard Gradle Android project structure:
- `app/` - Main application module
- `app/src/main/java/` - Java source files
- `app/src/main/res/` - Android resources
- `app/src/main/AndroidManifest.xml` - Android manifest
- `app/build.gradle` - App module build configuration
- `build.gradle` - Root build configuration
- `settings.gradle` - Project settings
- `gradlew` - Gradle wrapper script (Unix/Linux)
- `gradle/wrapper/` - Gradle wrapper files

## Requirements

### Android SDK Setup

Before building the project, you need to install the Android SDK:

1. **Install Android SDK** (Choose one option):
   - Install Android Studio which includes the Android SDK
   - Or install Android SDK command line tools

2. **Set up SDK location**:
   - Create a `local.properties` file in the project root
   - Add the line: `sdk.dir=/path/to/your/android-sdk`
   - Or set the `ANDROID_HOME` environment variable

Example `local.properties`:
```
sdk.dir=/home/username/Android/Sdk
```

### Java Version
- Java 8 or later is required (the project is configured for Java 8)

## Building the Project

Once the Android SDK is set up:

```bash
# Clean the project
./gradlew clean

# Build debug version
./gradlew assembleDebug

# Build release version
./gradlew assembleRelease

# Install debug version to connected device
./gradlew installDebug
```

## Key Changes Made

1. **Build System**: Migrated from Eclipse/Ant to Gradle
2. **Project Structure**: Reorganized to follow Gradle conventions
3. **Dependencies**: Updated to use AndroidX libraries
4. **API Levels**: Updated to target modern Android versions
5. **Manifest**: Cleaned up deprecated attributes

## Original Files Preserved

The following original files have been preserved for reference:
- `tools/` - Dictionary building tools
- `design/` - Design assets
- `proguard.cfg` - Original ProGuard configuration (replaced by app/proguard-rules.pro)
- `project.properties` - Original project properties (replaced by build.gradle)

## Migration Notes

- The package name remains: `com.linkomnia.android.Stroke5`
- Version code: 2
- Version name: 0.1 (from original strings.xml)
- Minimum SDK: API 14 (Android 4.0) - Updated from API 10 to meet Material Design library requirements
- Target SDK: API 34 (Android 14)
- Compile SDK: API 34
- Added `android:exported` attributes to manifest components for Android 12+ compatibility
