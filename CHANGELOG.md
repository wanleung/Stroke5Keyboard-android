# Changelog

## v1.0 — April 2026

**Package:** `com.wanleung.android.Stroke5`  
**Minimum Android:** 5.0 (API 21)  
**Target Android:** 15 (API 35)

### 🎉 First Modern Release

This is the first production release of Stroke5 Keyboard since the original 2012 prototype. The app has been fully rebuilt to work on modern Android devices (Android 5 through Android 15).

### What's New

**New Launcher App**
- A proper Android app now appears in your launcher when the keyboard is installed
- Step-by-step setup guide walks you through enabling and selecting the keyboard
- Live status indicators show whether the keyboard is enabled and active
- Buttons to open Input Method Settings and the system keyboard picker directly
- About page and Privacy Policy accessible from the launcher

**Keyboard Fixes**
- Chinese candidate word bar (選字欄) now displays correctly on all screen sizes
- Tablet layout fixed: candidate bar now shows 2 rows (stroke indicator + word selector) instead of a single squeezed row
- Keyboard no longer enters fullscreen mode, which was hiding the candidate bar on modern Android

**Compatibility**
- Migrated from Eclipse ADT (2012) to Gradle build system
- Updated from API 10 to API 35 (Android 15)
- Fixed all deprecated APIs (AsyncTask, PreferenceActivity, getColor, getDrawable, etc.)
- Replaced ImageButton with ImageView to fix invisible icons on Material Design
- Supports AndroidX

**Branding**
- Transferred to Wanleung's Workshop
- New application ID: `com.wanleung.android.Stroke5`
- Contact: info@wanleung.com

### Privacy

Stroke5 Keyboard collects no data. All processing is on-device. No internet permission is requested.

### Known Limitations

- Traditional Chinese only (Simplified Chinese not supported in this release)
- Dictionary based on the original Stroke5 1.0 table (26,000+ characters)

### Credits

Originally created in 2003 by Hong Kong Seniors IT Advocates (香港長者資訊天地).  
Android port by Wan Leung Wong — [Wanleung's Workshop](https://wanleung.com)  
Source: https://github.com/wanleung/Stroke5Keyboard-android
