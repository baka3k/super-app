package hi.baka.feature.react


import hi.baka.superapp.BaseSplitActivity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.widget.Button
import android.widget.FrameLayout
import com.facebook.hermes.reactexecutor.HermesExecutorFactory
import com.facebook.react.BuildConfig
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.soloader.SoLoader

class ReactActivity : BaseSplitActivity(), DefaultHardwareBackBtnHandler {
    private var mReactRootView: ReactRootView? = null
    private val OVERLAY_PERMISSION_REQ_CODE = 1
    private lateinit var btnCloseActivity: Button
    private lateinit var mReactInstanceManager: ReactInstanceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_react)
        btnCloseActivity = findViewById(R.id.btnCloseActivity)
        checkAndAskForOverlayPermission()
        loadReactNativeApp()
        btnCloseActivity.setOnClickListener {
            finish()
        }
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

    //
    override fun onPause() {
        mReactInstanceManager.onHostPause(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mReactInstanceManager.onHostResume(this, this)
    }

    override fun onDestroy() {
        mReactInstanceManager.onHostDestroy(this)
        mReactRootView?.unmountReactApplication()
        super.onDestroy()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun loadReactNativeApp() {
        SoLoader.init(applicationContext, false)
        val packages: List<ReactPackage> = PackageList(application).packages
        mReactRootView = ReactRootView(this)
        mReactInstanceManager = ReactInstanceManager.builder()
            .setJavaScriptExecutorFactory(HermesExecutorFactory())
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


    override fun invokeDefaultOnBackPressed() {
        finish()
    }
}