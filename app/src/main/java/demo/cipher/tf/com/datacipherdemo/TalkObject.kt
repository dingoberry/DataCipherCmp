package demo.cipher.tf.com.datacipherdemo

import java.io.Serializable

class TalkObject : Serializable {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 1L
    }

    var msg: String? = null;
    var deco: String? = null;

    constructor()

    constructor(m : String?, d : String?) {
        msg = m
        deco = d
    }
}