import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.security.MessageDigest

object HashUtils {

    const val STREAM_BUFFER_LENGTH = 1024
    fun compareCheckSum(originSum: String, filePath: String): Boolean {
        val digest = MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256)
        val fileSum = getCheckSumFromFile(digest = digest, filePath = filePath)
        return originSum.equals(fileSum, ignoreCase = true)
    }

    /**
     * HashUtils.getCheckSumFromFile(MessageDigest.getInstance(MessageDigestAlgorithm.SHA_256),path)
     * */
    fun getCheckSumFromFile(digest: MessageDigest, filePath: String): String {
        val file = File(filePath)
        return getCheckSumFromFile(digest, file)
    }

    fun getCheckSumFromFile(digest: MessageDigest, file: File): String {
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

fun ByteArray.toHexString(): String {
    return toHexString(0, this.size)
}

/**
 * Converts the byte array to HEX string.
 *
 * @param buffer
 * the buffer.
 * @return the HEX string.
 */
fun ByteArray.toHexString(offset: Int, length: Int): String {
    val sb = StringBuilder()
    for (i in offset until (offset + length)) {
        val b = this[i]
        val octet = b.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        sb.append(HEX_CHARS[firstIndex])
        sb.append(HEX_CHARS[secondIndex])
    }
    return sb.toString()
}

private val HEX_CHARS = "0123456789ABCDEF".toCharArray()