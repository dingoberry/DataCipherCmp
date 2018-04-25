package demo.cipher.tf.com.datacipherdemo.algorithm

import demo.cipher.tf.com.datacipherdemo.Config

internal abstract class Interpreter {

    var algorithm: Algorithm? = null

    abstract fun genAlgorithm()

    abstract fun translateToSecret(msg: String): String

    abstract fun translateToLocal(msg: String): String

    object Factory{
        fun loadRefInterpreter(type : String) : Interpreter? {
            when (type) {
                Config.RSA -> return RsaInterpreter()
                Config.EC -> return EcInterpreter()
            }
            return null
        }
    }
}