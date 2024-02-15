
# Key Architectural Drivers
-   Maximize the ability to distribute
-   Balance performance
-   Establish well separated / stable / sustainable boundary
#### Note:
>-  *Development costs depend on many different factors, so they are not included in proposal. This document only provides a technical assessment.*
>-  *SuperApps are ecosystems, not individual applications. The evaluation must be based on it*

# Application – Mobile framework decision
#### Native? Crossplatform or HybridPlatform?
![Alt text](https://i.imgur.com/PHJgG5m.png)
-   Native: IOS native, Android native
-   Interpreted Native: Flutter, React native…
-   Web Hybrid: Cordova, Ionic…

*=> Interpreted Native is best for Super App*
#### What’s best mobile framework?

|                |AppClips - IOS                          |Dynamic Features - Android                         |
|----------------|-------------------------------|-----------------------------|
|Compatibility   |Specifically for IOS devices   |Specifically for Android devices|
|Use cases           | One-time interactions, such as ordering food, making payments, or renting services.          |Delivering new content, features, or updates without requiring a full app download.           |
|Accessibility          |QR codes, NFC tags, or links, and they are primarily designed for iOS devices.|Dynamic Features are part of the Android App Bundle (AAB) format, and they can be delivered to Android devices using the Google Play Store.|
|Showcase          |Starbuck, Uber.|Google Map, Netflix, BIDV…|
|RuntimeLoader          |No – Maybe&limitation|No – Maybe&limitation|
#### Disadvantages
-   All release versions must be distributed through AppStore/PlayStore
-   Users have full control which version to update and use
-   ‘RuntimeLoader` mechanism does not really exist, only allow downloading application components that are available on the AppStore/PlayStore. 
-   It does not allow updating ore replacing individual components, to update, you must to through the Store
#### Flutter or React native?
|                |React Native|Flutter|
|----------------|-------------------------------|-----------------------------|
|Provider| Meta | Google |
|Activity| Stars:113k, Watchers:3.6k,Forks:23.9k | Stars:158k,Watchers:3.6k,Forks:26.2k |
|Developers| 1.8 million | 1.2 million |
|Rendering| JSRuntime(Hermes,V8,JavaScriptCore) | Skia |
|Language| JavaScript/TypeScript | Dart |
|Showcase| Facebook, Grab, Momo, Shopee, Team… | Google Pay, Ebay, BMV, Google Classroom… |
|Runtimeloader| Yes | Official: No – or Maybe |

- In official, Flutter team do not allow `dynamic loader` at runtime 
https://github.com/flutter/flutter/issues/14330
- There are unofficial code push services such as Shorebird and Chimera, but they are not as reliable as the official service.
https://github.com/ChimeraFlutter
https://shorebird.dev/

>*React Native is currently the best choice for developing Super apps.*
React native is best choice for runtime loader & environment for running miniApp

To be precise, React Native is the most suitable choice for the **MiniApp runtime environment**.
#### Mixed Architecture 

- Super apps are **not** built entirely on React Native. This is a **Native** application, but there are some modules that allow loading miniapp runtime - as multi-modules, multi-features
- In this time, we have chosen React Native as our runtime environment. And we can completely add other runtime modules in the future through Dynamic Features. Such as Flutter, Unity..etc

#### Application – Dynamic deployment
![Alt text](https://i.imgur.com/XvWwY1W.png)
Mechanism for distributing new Bundle packages
#### What’s best bundle server 

Developers can use push code without restrictions, as long as they comply with the app store's guidelines
- https://microsoft.github.io/code-push/faq/index.html
- https://learn.microsoft.com/en-us/appcenter/general/platform-limits
![Alt text](https://i.imgur.com/hmlBHtq.png)

#### Super App Architecture
![Alt text](https://i.imgur.com/ltU4Yj7.png)
**1. MiniApp:**
-   Separate application by modules and based on Business Core SDK for developing screen UI & logic for each module. It’s JsBundle

**2. Security Layer:** 
-   Manage mini module app permission
-   Verify signature, encrypt data, jsbundle
-   Provide Biometric: Fingerprint…

**3. Native View:** 
-   Provide common native view to ensure performance: RecycleView, TableView, Canvas view, Chart view..etc…

**4. Business CoreService:** 
-   Core service of application, such as: Account, Loyalty, Payment..etc

**5. JavaScript Runtime:**
-   In charge of loading mini apps into the application through Jsbundle packages at runtime
-   JS Bundle Loader: Management JsBundle, version up…
-   Hermes(recommend) or V8/JavaScriptCore

#### Mini App SDK
Use for vendors to develop their mini apps
![Alt text](https://i.imgur.com/Wakt1ny.png)
-   Common UI: UI elements shared between many modules, ensuring uniformity in theme, font, and color.
-   Core Service: Provides an interface that allows mini apps to use the core business logic of the main app.
-   Security: Provides the following features: bundle validation, main app validation, checking and requesting permissions to the main app. 

>Note: Mini apps are not allowed to interact directly with each other, and their interactions are also limited, All Interactions must go through the Main App

# Technical points that need to be resolved.
-   Integration with existing app
-   Mini App & Permission
-   Multiple Bundle/Mini apps
-   Move special engines(Unity, React native) to Dynamic features
-   T.B.D
#### Technical points 1 – Integration with existing app
-   Reference: https://reactnative.dev/docs/integration-with-existing-apps​
#### Technical points 2 - Mini App & Permission
![Alt text](https://i.imgur.com/yu3sN7M.png)

-   App Permission Module:
    -   Has database to store status of permission of mini-app
app permission module will define list permission mini-app can request
    -   When Mini app request permission, app Permission module will double check on native app to ensure supper app was grant permission and return to Mini app 
    -   MainApp app just work with white list Mini app 
#### Technical points 3 – Multiple Bundle/Mini apps
-   Reference: https://github.com/varunon9/react-native-multiple-bundle
#### Technical points 4 – Move Special engines to Dynamic features
![Alt text](https://i.imgur.com/oTUqjEC.png)

- JS Runtime module:  Mini app runtime environment(React Native, Flutter, Unity..etc)
- Dynamic Feature module: Downloaded on demand through the Dynamic Feature mechanism of Android
- Static module: Pre-installed when installing the app from the store
>Note: Special engines such as ReactNative, Flutter, Unity, etc., need to be placed in dynamic features, not added directly to the App Module.

- Reference: https://github.com/baka3k/super-app/tree/main/feature/feature_react

#### Module Restructure
![Alt text](https://i.imgur.com/5CmikzU.png)
Need to be considered for balance:
-  Volume of components
-  Complexity of dependencies
-  Technical feasibility, quality of available resources, and deployment time
>Required to strictly comply with DI
# Super App - Evolution
Recommended development steps for developers who want to develop a super app from an existing app

![Alt text](https://i.imgur.com/iq1rdxm.png)

# Environment Setup

-   IDE: Android Studio Jellyfish | 2023.3.1 Canary 6
-   Android Gradle Plugin: 8.2.1 
-   React: 0.73.2
-   Hermes: 0.64.2

Before using the Runtime Loader module, you'll need to install the necessary Node module.
```
cd baka3k-react-native
npm install
```

## Get API KEY 
-    Obtain an Access key from https://developer.themoviedb.org/docs/getting-started.
-    Update the MOVIEDB_ACCESS_KEY value in the secrets.defaults.properties file.

# Demo
https://github.com/baka3k/super-app/assets/8104076/d67c548b-7038-49e6-9a1a-1b14eeb1d191
