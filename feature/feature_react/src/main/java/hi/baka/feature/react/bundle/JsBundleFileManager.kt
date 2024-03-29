package hi.baka.feature.react.bundle

import HashUtils
import java.io.File

class JsBundleFileManager(rootFolder: File) {
    private val bundleFolder: File = File(rootFolder, RN_BUNDLE_FOLDER)

    init {
        if (!rootFolder.exists()) {
            rootFolder.mkdirs()
        }
        if (!bundleFolder.exists()) {
            bundleFolder.mkdirs()
        }
    }

    /**
     * return JsBundleFile by jsBundleFileName
     * if bundleFolder has many files, consider using index to optimize performance
     * */
    public fun getJsBundleFile(jsBundleFileName: String = ""): JsBundleFile? {
        val bundleFiles =
            bundleFolder.listFiles()?.filter { file: File -> file.name.equals(jsBundleFileName) }
        return if (!bundleFiles.isNullOrEmpty()) {
            val bundleFile = bundleFiles.first()
            JsBundleFile(
                miniAppId = bundleFile.name,
                sha = HashUtils.generateCheckSum(
                    file = bundleFile
                ),
                jsBundleFile = bundleFile.absolutePath
            )
        } else {
            null
        }
    }

    /**
     * return All JsBundleFile in bundleFolder.
     * emptyArray if no file found
     * */
    public fun getAllJsBundleFiles(): List<JsBundleFile> {
        return bundleFolder.listFiles()?.map { bundleFile ->
            JsBundleFile(
                miniAppId = bundleFile.name,
                sha = HashUtils.generateCheckSum(
                    file = bundleFile
                ),
                jsBundleFile = bundleFile.absolutePath
            )
        } ?: emptyList()
    }

    public fun getJsBundleFolder(): File {
        return bundleFolder
    }

    companion object {
        const val RN_BUNDLE_FOLDER = "RNBundle"
    }
}