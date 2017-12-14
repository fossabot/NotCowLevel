package cn.imrhj.cowlevel.utils

import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * AES加密工具类
 * Created by rhj on 14/12/2017.
 */
object AESUtils {
    @Throws(Exception::class)
    fun encrypt(seed: String, cleartext: String): String {
        val rawKey = deriveKeyInsecurely(seed, 32).getEncoded()
        val result = encrypt(rawKey, cleartext.toByteArray())
        return toHex(result)
    }

    @Throws(Exception::class)
    fun decrypt(seed: String, encrypted: String): String {
        val rawKey = deriveKeyInsecurely(seed, 32).getEncoded()
        val enc = toByte(encrypted)
        val result = decrypt(rawKey, enc)
        return String(result)
    }

    private fun deriveKeyInsecurely(password: String, keySizeInBytes: Int): SecretKey {
        val passwordBytes = password.toByteArray(StandardCharsets.US_ASCII)
        return SecretKeySpec(
                InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(
                        passwordBytes, keySizeInBytes),
                "AES")
    }

    @Throws(Exception::class)
    private fun encrypt(raw: ByteArray, clear: ByteArray): ByteArray {
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, IvParameterSpec(ByteArray(cipher.getBlockSize())))
        return cipher.doFinal(clear)
    }

    @Throws(Exception::class)
    private fun decrypt(raw: ByteArray, encrypted: ByteArray): ByteArray {
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(ByteArray(cipher.getBlockSize())))
        return cipher.doFinal(encrypted)
    }

    private fun toHex(txt: String): String {
        return toHex(txt.toByteArray())
    }

    private fun fromHex(hex: String): String {
        return String(toByte(hex))
    }

    private fun toByte(hexString: String): ByteArray {
        val len = hexString.length / 2
        val result = ByteArray(len)
        for (i in 0 until len)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16)!!.toByte()
        return result
    }

    private fun toHex(buf: ByteArray?): String {
        if (buf == null)
            return ""
        val result = StringBuffer(2 * buf.size)
        for (i in buf.indices) {
            appendHex(result, buf[i])
        }
        return result.toString()
    }

    private val HEX = "0123456789ABCDEF"

    private fun appendHex(sb: StringBuffer, b: Byte) {
        val intB = b.toInt()
        sb.append(HEX[(intB shr 4) and 0x0f]).append(HEX[intB and 0x0f])
    }
}