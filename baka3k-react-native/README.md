This is a new [**React Native**](https://reactnative.dev) project, bootstrapped using [`@react-native-community/cli`](https://github.com/react-native-community/cli).

# Getting Started

>**Note**: Make sure you have completed the [React Native - Environment Setup](https://reactnative.dev/docs/environment-setup) instructions till "Creating a new application" step, before proceeding.
# Install Node Module
To ensure a stable React Native environment, you must install the node modules before syncing the Android Gradle build."
```
cd baka3k-react-native
npm install
```
# Build bundle 
```kotlin
npx react-native bundle --platform android --dev false --entry-file index.js --bundle-output index.android.bundle --assets-dest android/app/src/main/res
```
