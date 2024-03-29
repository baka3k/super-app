package hi.baka.feature.react.bundle


import android.util.Log
import androidx.annotation.WorkerThread
import hi.baka.feature.react.security.JsBundleVerification
import java.io.File

class JsBundleLoader(
    private val jsBundleFileManager: JsBundleFileManager,
    private val jsBundleVerification: JsBundleVerification
) {
    /**
     * Load BundleFile by jsBundleFileName & Mini App
     * should run in workerthread
     * */
    @WorkerThread
    fun loadJsBundleFile(
        jsBundleFileName: String = "",
        miniAppId: String = ""
    ): Result<JsBundleFile> {
        val jsBundleFile = jsBundleFileManager.getJsBundleFile(jsBundleFileName)
        if (jsBundleFile != null) {
            val file = File(jsBundleFile.jsBundleFile)
            Log.d("test","#loadJsBundleFile(): ${file.absolutePath}")
            if (file.exists()) {
                val result = jsBundleVerification.verifyJsBundle(
                    miniAppId = miniAppId,
                    file = file
                )
                Log.d("test","#loadJsBundleFile(): $result - ${file.absolutePath}")
                if (result.isSuccess) {
                    return Result.success(jsBundleFile)
                } else if (result.isFailure) {
                    val exception = result.exceptionOrNull()
                    if (exception == null) {
                        return Result.failure(Exception("Unknow Error"))
                    } else {
                        return Result.failure(exception)
                    }
                }
            }
        }
        return Result.failure(exception = Exception("JsBundle is not found"))
    }

}