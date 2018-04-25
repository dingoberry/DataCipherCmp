package demo.cipher.tf.com.datacipherdemo

import java.io.Serializable

class TalkObject : Serializable {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 1L
    }

    var msg: String? = null;
    var deco: String? = null;
    var tag: String? = null;

    constructor()

    constructor(m : String?, d : String?, t : String?) {
        msg = m
        deco = d
        tag = t
    }
}