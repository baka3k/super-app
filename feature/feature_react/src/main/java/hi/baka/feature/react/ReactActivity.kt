package hi.baka.feature.react


import hi.baka.superapp.BaseSplitActivity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.widget.FrameLayout
import com.facebook.react.BuildConfig
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.soloader.SoLoader
class ReactActivity : BaseSplitActivity(),DefaultHardwareBackBtnHandler {
    private var mReactRootView: ReactRootView? = null
    private val OVERLAY_PERMISSION_REQ_CODE = 1

    private var mReactInstanceManager: ReactInstanceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_react)
        SoLoader.init(this, false)
        loadReactNativeApp()
    }
    private fun checkAndAskForOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
            }
        }
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()

        if (mReactInstanceManager != null) {
            mReactInstanceManager!!.onHostPause(this)
        }
    }

    override fun onResume() {
        super.onResume()

        if (mReactInstanceManager != null) {
            mReactInstanceManager!!.onHostResume(this, this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mReactInstanceManager != null) {
            mReactInstanceManager!!.onHostDestroy(this)
        }
        if (mReactRootView != null) {
            mReactRootView!!.unmountReactApplication()
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager?.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun loadReactNativeApp() {
        println("loading RN app")
        val packages: List<ReactPackage> = PackageList(application).packages
        mReactRootView = ReactRootView(this)
        mReactInstanceManager = ReactInstanceManager.builder()
            .setApplication(application)
            .setCurrentActivity(this)
            .setBundleAssetName("index.android.bundle")
            .setJSBundleFile("assets://index.android.bundle")
            .setJSMainModulePath("index")
            .addPackages(packages)
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build()
        mReactRootView?.startReactApplication(mReactInstanceManager, "baka3k", null)
        val contentView = findViewById<FrameLayout>(R.id.contentView)
        contentView.removeAllViews()
        contentView.addView(mReactRootView)
    }
}