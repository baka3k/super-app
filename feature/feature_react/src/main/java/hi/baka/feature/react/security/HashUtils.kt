import hi.baka.feature.data.toHexString
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.security.MessageDigest

object HashUtils {
    private const val STREAM_BUFFER_LENGTH = 1024
    fun checkSum(
        originSum: String,
        digest: MessageDigest = MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256),
        filePath: String
    ): Boolean {
        val fileSum = generateCheckSum(digest = digest, filePath = filePath)
        return originSum.equals(fileSum, ignoreCase = true)
    }

    fun checkSum(
        originSum: String,
        digest: MessageDigest = MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256),
        inputStream: InputStream
    ): Boolean {
        val fileSum = generateCheckSum(digest = digest, inputStream = inputStream)
        return originSum.equals(fileSum, ignoreCase = true)
    }

    /**
     * HashUtils.getCheckSumFromFile(MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256),path)
     * */
    fun generateCheckSum(
        digest: MessageDigest = MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256),
        filePath: String
    ): String {
        val file = File(filePath)
        return generateCheckSum(digest, file)
    }

    fun generateCheckSum(
        digest: MessageDigest = MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256),
        inputStream: InputStream
    ): String {
        val byteArray = updateDigest(digest, inputStream).digest()
        return byteArray.toHexString()
    }

    fun generateCheckSum(
        digest: MessageDigest = MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256),
        file: File
    ): String {
        val fis = FileInputStream(file)
        val byteArray = updateDigest(digest, fis).digest()
        fis.close()
        return byteArray.toHexString()
    }

    /**
     * Reads through an InputStream and updates the digest for the data
     *
     * @param digest The MessageDigest to use (e.g. MD5)
     * @param data Data to digest
     * @return the digest
     */
    private fun updateDigest(digest: MessageDigest, data: InputStream): MessageDigest {
        val buffer = ByteArray(STREAM_BUFFER_LENGTH)
        var read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)
        while (read > -1) {
            digest.update(buffer, 0, read)
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)
        }
        return digest
    }

}

object MessageDigestAlgorithm {
    const val MD2 = "MD2"
    const val MD5 = "MD5"
    const val SHA_1 = "SHA-1"
    const val SHA_224 = "SHA-224"
    const val SHA_256 = "SHA-256"
    const val SHA_384 = "SHA-384"
    const val SHA_512 = "SHA-512"
    const val SHA_512_224 = "SHA-512/224"
    const val SHA_512_256 = "SHA-512/256"
    const val SHA3_224 = "SHA3-224"
    const val SHA3_256 = "SHA3-256"
    const val SHA3_384 = "SHA3-384"
    const val SHA3_512 = "SHA3-512"
}