package com.wuruoye.ichp.base.util

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.MGF1ParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

/**
 * Created by wuruoye on 2017/11/6.
 * this file is to do
 */
object SecretUtil {
    private val PUBLIC_RSA_KEY =
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnFC95W67i6GNfVzoAf9zVcunr\n" +
                    "DM25lLBum+47JrvL6PzZChK9muONED6BTZjhsw77FJpBxKqhhLYKxEPZYdQnAWmy\n" +
                    "s+JfJX36HDm/Pn14oG4N7S9MRIDTwFhBc+qHhTZcFIoR0addScJPLigkRyVUppi0\n" +
                    "KvvtISI0SoVkj6VzzwIDAQAB\n"

    fun getPublicSecret(content: String): String{
        val buffer = Base64.decode(PUBLIC_RSA_KEY, Base64.DEFAULT)
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(buffer)
        val key = keyFactory.generatePublic(keySpec) as PublicKey

        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding",
                "BC")
        cipher.init(Cipher.ENCRYPT_MODE, key, OAEPParameterSpec("SHA-256",
                "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT))
        val data = cipher.doFinal(content.toByteArray())
        return Base64.encodeToString(data, Base64.DEFAULT)
    }
}