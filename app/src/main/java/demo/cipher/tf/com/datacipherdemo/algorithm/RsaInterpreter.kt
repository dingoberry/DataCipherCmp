package demo.cipher.tf.com.datacipherdemo.algorithm

import demo.cipher.tf.com.datacipherdemo.Config
import demo.cipher.tf.com.datacipherdemo.utils.CipherUtils
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

internal class RsaInterpreter : Interpreter() {

    private val TRANS = "RSA/ECB/PKCS1Padding"

    override fun genAlgorithm() {
        val pair = KeyPairGenerator.getInstance(Config.RSA).genKeyPair()
        this.algorithm = Algorithm(pair.public.encoded, pair.private.encoded)
    }

    override fun translateToSecret(msg: String): String {
        return CipherUtils.encodeData(TRANS, KeyFactory.getInstance(Config.RSA)
                .generatePublic(X509EncodedKeySpec(algorithm?.encode)), msg)
    }

    override fun translateToLocal(msg: String): String {
        return CipherUtils.decodeData(TRANS, KeyFactory.getInstance(Config.RSA)
                .generatePrivate(PKCS8EncodedKeySpec(algorithm?.decode)), msg)
    }
}