# Stroke5 Keyboard for Android

A Chinese input method using the Stroke5 (五筆) system for Android devices.

## Quick Start

1. **Install Android SDK** (if not already installed):
   - Download Android Studio or Android SDK command line tools
   - Set up the SDK path in `local.properties` or `ANDROID_HOME` environment variable

2. **Build the project**:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Install to device**:
   ```bash
   ./gradlew installDebug
   ```

## Project Status

✅ **Successfully migrated to Gradle and building!** 
- Updated from Eclipse/Ant to Gradle build system
- Fixed minimum SDK requirement (API 14) for Material Design library compatibility
- Added Android 12+ manifest compatibility
- APK successfully builds: `app/build/outputs/apk/debug/app-debug.apk`

See `GRADLE_MIGRATION.md` for complete migration details.

## Features

- Stroke5 (五筆) Chinese input method
- Android Input Method Editor (IME)
- Customizable keyboard layout
- Dictionary-based word completion

## License

GNU General Public License v3.0 - See `LICENSE` file for details

## Author

Wan Leung Wong (wanleung@linkomnia.com)  
Copyright (C) 2012 LinkOmnia Ltd.
