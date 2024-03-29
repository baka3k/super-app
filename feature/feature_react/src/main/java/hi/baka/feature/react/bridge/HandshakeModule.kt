package hi.baka.feature.react.bridge

import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.util.Log
import com.baka3k.core.common.logger.Logger
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import hi.baka.feature.react.security.SignatureUtils

import java.security.MessageDigest


class HandshakeModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
    private val packageManager = reactContext.packageManager
    private val packageName = reactContext.packageName
    override fun getName(): String {
        return "HandshakeModule"
    }

    /**
     * 1. Main App check miniApp Id
     * 2. Main App return Signature to MiniApp
     * 3. MiniApp check Signature of Main App
     * 4. if success, finish Handshake
     * */
    @ReactMethod
    fun handshakeMainApp(miniappId: String, callback: Callback) {
        Logger.d("HandshakeModule", "#handShakeMainApp(): $name")
        val verifyResult = verifyMiniApp(miniappId = miniappId)
        if (verifyResult) {
            val signatureMainApp = getApplicationSignature()
            Logger.d("HandshakeModule", "#handShakeMainApp() - signatureMainApp: $signatureMainApp")
            callback.invoke(signatureMainApp)
        } else {
            callback.invoke("MiniApp is blocked")
        }
    }

    private fun verifyMiniApp(miniappId: String): Boolean {
        val miniappId = getBundleInfo(miniappId = miniappId)

        return true// by pass for test
    }

    private fun getBundleInfo(miniappId: String): String {
        // query checksum by miniapp ID
        return ""
    }

    private fun getApplicationSignature(): String {
        val applicationSignature = SignatureUtils.getApplicationSignature(
            packageManager = packageManager,
            packageName = packageName
        )
        if (applicationSignature.isSuccess) {
            val signature = applicationSignature.getOrNull()
            return signature ?: ""
        } else {
            val exception = applicationSignature.exceptionOrNull()
            Logger.d("HandshakeModule", "#getApplicationSignature(): $exception")
            return ""
        }
    }
}