package demo.cipher.tf.com.datacipherdemo.utils

import java.security.Key
import javax.crypto.Cipher

object CipherUtils {

    private fun processData(mode: Int, trans: String, prov: String?, key: Key, data: ByteArray): ByteArray {
        val c = if (null == prov) {
            Cipher.getInstance(trans)
        } else {
            Cipher.getInstance(trans, prov)
        }
        c.init(mode, key)
        return c.doFinal(data)
    }

    fun encodeData(trans: String, key: Key, data: String): String {
        return Base64Utils.toString(processData(Cipher.ENCRYPT_MODE, trans, null, key, data.toByteArray()))
    }

    fun decodeData(trans: String, key: Key, data: String): String {
        return String(processData(Cipher.DECRYPT_MODE, trans, null, key, Base64Utils.toByte(data)))
    }

    fun encodeData(trans: String, prov: String, key: Key, data: String): String {
        return Base64Utils.toString(processData(Cipher.ENCRYPT_MODE, trans, prov, key, data.toByteArray()))
    }

    fun decodeData(trans: String, prov: String, key: Key, data: String): String {
        return String(processData(Cipher.DECRYPT_MODE, trans, prov, key, Base64Utils.toByte(data)))
    }
}