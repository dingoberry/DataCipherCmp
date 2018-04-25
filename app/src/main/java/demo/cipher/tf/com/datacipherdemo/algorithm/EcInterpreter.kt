package demo.cipher.tf.com.datacipherdemo.algorithm

import demo.cipher.tf.com.datacipherdemo.Config
import demo.cipher.tf.com.datacipherdemo.utils.CipherUtils
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec


internal class EcInterpreter : Interpreter() {

    private val TRANS = "ECIES"
    private val PROV = "BC"

    override fun genAlgorithm() {
        val kg = KeyPairGenerator.getInstance(Config.EC, Config.BC)
        kg.initialize(256, SecureRandom())
        val pair = kg.genKeyPair()
        this.algorithm = Algorithm(pair.public.encoded, pair.private.encoded)
    }

    override fun translateToSecret(msg: String): String {
        return CipherUtils.encodeData(TRANS, PROV, KeyFactory.getInstance(Config.EC)
                .generatePublic(X509EncodedKeySpec(algorithm?.encode)), msg)
    }

    override fun translateToLocal(msg: String): String {
        return CipherUtils.decodeData(TRANS, PROV, KeyFactory.getInstance(Config.EC)
                .generatePrivate(PKCS8EncodedKeySpec(algorithm?.decode)), msg)
    }
}