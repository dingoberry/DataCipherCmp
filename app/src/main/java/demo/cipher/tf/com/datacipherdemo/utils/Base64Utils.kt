package demo.cipher.tf.com.datacipherdemo.utils

import android.util.Base64

internal object Base64Utils {
    fun toString(data : ByteArray) : String{
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    fun toByte(base64: String) : ByteArray {
        return Base64.decode(base64, Base64.DEFAULT)
    }
}