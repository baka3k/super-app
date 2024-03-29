package hi.baka.feature.react.bridge

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import hi.baka.feature.compose.person.ui.PersonView

class HiAppReactPackage : ReactPackage {

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): MutableList<ViewManager<*, *>> {
        return mutableListOf(
            ReactImageManager(reactContext),
            PersonViewManager(reactContext)
        )
    }

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): MutableList<NativeModule> {
        return mutableListOf(PersonModule(reactContext))
    }
}