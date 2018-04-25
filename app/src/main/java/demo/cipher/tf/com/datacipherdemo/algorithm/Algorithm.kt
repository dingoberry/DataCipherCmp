package demo.cipher.tf.com.datacipherdemo.algorithm

import demo.cipher.tf.com.datacipherdemo.utils.Base64Utils

internal class Algorithm constructor(e: ByteArray?, d: ByteArray?) {
    val encode: ByteArray?
    val decode: ByteArray?

    init {
        encode = e
        decode = d
    }

    fun getFormatEncode(): String {
        return Base64Utils.toString(encode!!)
    }

    fun getFormatDecode(): String {
        return Base64Utils.toString(decode!!)
    }
}