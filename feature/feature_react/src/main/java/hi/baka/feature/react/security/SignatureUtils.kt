package hi.baka.feature.react.security

import android.content.pm.PackageManager
import android.os.Build
import hi.baka.feature.data.toHexString
import java.security.MessageDigest

object SignatureUtils {
    public fun getApplicationSignature(
        packageManager: PackageManager,
        packageName: String
    ): Result<String> {
        try {
            val signature = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // New signature
                val signingInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo
                if (signingInfo.hasMultipleSigners()) {
                    return Result.failure(SecurityException("Do not allow multi Signature"))
                } else {
                    val apkContentsSigners = signingInfo.apkContentsSigners
                    if (apkContentsSigners.isEmpty()) {
                        return Result.failure(SecurityException("Signature is Empty"))
                    } else {
                        val signature = apkContentsSigners.first()
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(signature.toByteArray())
                        digest.digest().toHexString()
                    }
                }
            } else {
                val signingInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ).signatures
                if (signingInfo.isEmpty()) {
                    return Result.failure(SecurityException("Signature is Empty"))
                } else {
                    val signature = signingInfo.first()
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(signature.toByteArray())
                    digest.digest().toHexString()
                }
            }
            return Result.success(signature)
        } catch (e: PackageManager.NameNotFoundException) {
            return Result.failure(e)
        }
    }
}