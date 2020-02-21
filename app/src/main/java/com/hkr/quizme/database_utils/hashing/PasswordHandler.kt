package com.hkr.quizme.database_utils.hashing

import android.util.Log
import java.lang.Exception
import java.lang.StringBuilder
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.KeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

class PasswordHandler() {
    fun hash(password: String): String {
        val digest = MessageDigest.getInstance("SHA-512")
        digest.reset()
        digest.update(password.toByteArray())
        return String.format("%0128x", BigInteger(1, digest.digest()))
    }
}