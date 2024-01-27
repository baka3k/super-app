import groovy.lang.Closure

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
// project name include
rootProject.name = "SuperAppStructure"
include(":app")

include(":core_navigation")
include(":core_data")
include(":core_network")
include(":core_model")
//include(":core_datastore")
//include(":datastore_proto")
include(":core_common")
include(":core_database")
include(":core_ui")
include(":core_splitInstall")

include(":feature_movie_list")
include(":feature_movie_detail")
include(":feature_person")
// project location

project(":core_navigation").projectDir = File(rootDir, "core/core_navigation")
project(":core_data").projectDir = File(rootDir, "core/core_data")
project(":core_network").projectDir = File(rootDir, "core/core_network")
project(":core_model").projectDir = File(rootDir, "core/core_model")
//project(":core_datastore").projectDir = File(rootDir, "core/core_datastore")
//project(":datastore_proto").projectDir = File(rootDir, "core/datastore_proto")
project(":core_common").projectDir = File(rootDir, "core/core_common")
project(":core_database").projectDir = File(rootDir, "core/core_database")
project(":core_ui").projectDir = File(rootDir, "core/core_ui")
project(":core_splitInstall").projectDir = File(rootDir, "core/core_splitInstall")

project(":feature_movie_list").projectDir = File(rootDir, "feature/feature_movie_list")
project(":feature_movie_detail").projectDir = File(rootDir, "feature/feature_movie_detail")
project(":feature_person").projectDir = File(rootDir, "feature/feature_person")


include(":feature_react")
project(":feature_react").projectDir = File(rootDir, "feature/feature_react")
/// react native config
includeBuild("$rootDir/baka3k-react-native/node_modules/@react-native/gradle-plugin")
apply(from = "$rootDir/baka3k-react-native/node_modules/@react-native-community/cli-platform-android/native_modules.gradle")
val applyNativeModules: Closure<Any> = extra.get("applyNativeModulesSettingsGradle") as Closure<Any>
applyNativeModules(settings)
