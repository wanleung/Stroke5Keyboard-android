# Stroke5 Keyboard for Android

A Chinese stroke-based input method (IME) for Android, designed for elders and children who find traditional Chinese input methods difficult to learn.

## About Stroke5

Stroke5 maps the five fundamental Chinese stroke types to just five keys:

| Key | Stroke | Type |
|-----|--------|------|
| `N` | 横 | Horizontal / Rise |
| `M` | 撇 | Throw away |
| `,` | 點 | Dot / Press down |
| `.` | 折 | Zigzag |
| `/` | 豎 | Vertical |

Because Chinese is taught using stroke order, anyone who has learned to write Chinese characters can immediately use Stroke5 — no Pinyin or Zhuyin knowledge required. The one-handed five-key design also makes it ideal for users with limited mobility.

The method was originally created in Hong Kong in 2003 by a group of seniors for seniors. This Android app brings it to modern devices.

## Features

- Stroke-based Chinese character input (no Pinyin required)
- Dictionary of over 26,000 characters with phrase support
- Works on phones and tablets (portrait and landscape)
- Built-in launcher app guides users through IME setup step-by-step
- Supports Traditional Chinese (繁體中文)
- Fully offline — no internet connection required
- Privacy-first: no data collection, no analytics

## Requirements

- Android 5.0 (API 21) or higher
- Tested up to Android 15 (API 35)

## Installation

1. Download `app-release.apk` from the [Releases](https://github.com/wanleung/Stroke5Keyboard-android/releases) page
2. Enable "Install from unknown sources" in your device settings
3. Open the APK to install
4. Open the **Stroke5 Keyboard** app and follow the two setup steps:
   - **Step 1** — Enable the keyboard in Input Method Settings
   - **Step 2** — Select Stroke5 Keyboard as your active input method

## Building from Source

### Prerequisites

- JDK 17+
- Android SDK (API 34, Build Tools 33+)
- Set `sdk.dir` in `local.properties`

### Debug build

```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK

```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

### Release AAB (Play Store)

```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

### Signing setup

Create a `keystore.properties` file in the project root (this file is gitignored):

```properties
storeFile=/path/to/your/keystore.jks
storePassword=your_store_password
keyAlias=your_key_alias
keyPassword=your_key_password
```

## Project Structure

```
src/                        Java source files
  com/wanleung/android/Stroke5/
    MainActivity.java       Launcher: IME setup guide
    StrokeFiveKeyboard.java Main IME service
    CandidateView.java      Candidate word selection bar
    IMEKeyboardView.java    Keyboard view
    IMESwitch.java          Input mode switching logic
    WordProcessor.java      Dictionary lookup
    Setting.java            Settings activity
res/                        Android resources (layouts, drawables, strings)
assets/
  about.html                About page
  privacy_policy.html       Privacy policy
  *.db                      Stroke5 character dictionary
```

## Privacy

Stroke5 Keyboard does not collect, store, or transmit any personal data. All text processing happens entirely on-device. See [Privacy Policy](assets/privacy_policy.html) for details.

## License

This program is free software: you can redistribute it and/or modify it under the terms of the **GNU General Public License v3** as published by the Free Software Foundation.

See [LICENSE](LICENSE) for the full license text.

## Credits

**Stroke5 Chinese Input Method** — originally created in 2003 by Hong Kong Seniors IT Advocates (香港長者資訊天地):
- 梁敬文 Leung King Man
- 王淑美 Amy Wong
- 陳川 Chan Chuen
- and many more contributors

Android port and modernisation by **Wan Leung Wong** — [Wanleung's Workshop](https://wanleung.com)  
Contact: info@wanleung.com  
Issues & suggestions: [GitHub Issues](https://github.com/wanleung/Stroke5Keyboard-android/issues)

