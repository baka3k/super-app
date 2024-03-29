package hi.baka.feature.react.security

import HashUtils
import hi.baka.feature.react.bundle.JsBundleFile
import java.io.File
import java.io.InputStream

class JsBundleVerification {
    fun verifyJsBundle(miniAppId: String, file: File): Result<Boolean> {
        val whiteList = getShaMiniApps(miniAppId)
        if (whiteList.isEmpty()) {
            return Result.failure(SecurityException("JsBundle is not found in whiteList"))
        }
        val value = HashUtils.checkSum(
            originSum = whiteList,
            filePath = file.absolutePath
        )

        if (!value) {
            return Result.failure(SecurityException("JsBundle is not trusted"))
        } else {
            return Result.success(true)
        }
    }

    @Throws(SecurityException::class)
    fun verifyJsBundle(miniAppId: String, inputStream: InputStream): Result<Boolean> {
        val whiteList = getShaMiniApps(miniAppId)
        if (whiteList.isEmpty()) {
            return Result.failure(SecurityException("JsBundle is not found in whiteList"))
        }

        val value = HashUtils.checkSum(
            originSum = whiteList,
            inputStream = inputStream
        )
        if (!value) {
            return Result.failure(SecurityException("JsBundle is not trusted"))
        } else {
            return Result.success(true)
        }
    }

    /**
     * return whiteList hash of JsBundle from server by miniAppId
     * */
    private fun getShaMiniApps(miniAppId: String): String {
        // whiteList will be downloaded from server
        // for testing, we bypass and fixed the whiteList is :"index.android.bundle"
        val jsBundleFiles = initTestMiniApp()
        val jsBundleFile = jsBundleFiles.filter { it.miniAppId.equals(miniAppId) }
        if (jsBundleFile.isNotEmpty()) {
            val file = jsBundleFile.first()
            val sha = file.sha
            return sha
        }
        return ""
    }

    private fun initTestMiniApp(): List<JsBundleFile> {
        val list = mutableListOf<JsBundleFile>()
        list.add(
            JsBundleFile(
                miniAppId = "person",
                sha = "c1870b7a0a43cf433a4db07760a65ea743651933b513a7679e57e53ba5581da4",
            )
        )
        list.add(
            JsBundleFile(
                miniAppId = "person2",
                sha = "9acbbf632ab287e22f26e855c65b66b575755302e2798d6115cffba9f1518c6e",
            )
        )
        list.add(
            JsBundleFile(
                miniAppId = "person3",
                sha = "7be14ee9ee0ace0cad0b22f0e3aa226cae098cf34b15feccecfc04dd4d3116a1",
            )
        )
        list.add(
            JsBundleFile(
                miniAppId = "person4",
                sha = "ece44715608c2a28688e1866b477731decc7fc7310fc16f6e667912921d79b32",
            )
        )

        return list
    }
}