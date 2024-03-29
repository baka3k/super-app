package hi.baka.feature.react.bridge

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class PersonModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "PersonModule"
    }
    @ReactMethod
    fun loadData(name: String, location: String) {
        Log.d("PersonModule", "Create event called with name: $name and location: $location")
    }
}