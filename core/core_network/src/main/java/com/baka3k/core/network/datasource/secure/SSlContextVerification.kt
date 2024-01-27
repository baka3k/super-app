package com.baka3k.core.network.datasource.secure

import android.content.res.AssetManager
import com.baka3k.core.common.logger.Logger
import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import javax.inject.Inject
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class SSlContextVerification @Inject constructor(
    private val assetManager: AssetManager
) {
    val trustManager = trustManagerForCertificates(trustedCertificatesInputStream())
    val sslSocketFactory = initSSLFactory()
    val pinnings = mapOf(
        "sha256/5VLcahb6x4EvvFrCF2TePZulWqrLHS2jCg9Ywv6JHog=" to "api.themoviedb.org",
    )

    @Throws(
        KeyManagementException::class
    )
    private fun initSSLFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), SecureRandom())
        return sslContext.socketFactory
    }

    private fun trustedCertificatesInputStream(): InputStream =
        assetManager.open("themoviedb.org.cer")

    //assetManager.open("developer.android.com.cer") //test fail case
    @Throws(
        IllegalArgumentException::class, IllegalStateException::class
    )
    private fun trustManagerForCertificates(inputStream: InputStream): X509TrustManager {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificates: Collection<Certificate> =
            certificateFactory.generateCertificates(inputStream)
        try {
            inputStream.close()
        } catch (e: IOException) {
            Logger.e("trustManagerForCertificates can not close inputStream")
        }
        if (certificates.isEmpty()) {
            throw IllegalArgumentException("expected non-empty set of trusted certificates")
        }
        val password = getPassword()
        val keyStore = newEmptyKeyStore(password)
        for ((index, certificate) in certificates.withIndex()) {
            val certificateAlias = (index).toString()
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }
        val keyManagerFactory: KeyManagerFactory = KeyManagerFactory.getInstance(
            KeyManagerFactory.getDefaultAlgorithm()
        )
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        trustManagerFactory.init(keyStore)
        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
        if ((trustManagers.size != 1) || trustManagers[0] !is X509TrustManager) {
            throw  IllegalStateException("Unexpected default trust managers: ${trustManagers.contentToString()}")
        }
        return trustManagers[0] as X509TrustManager
    }

    private fun getPassword(): CharArray = "baka3k".toCharArray()

    @Throws(AssertionError::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        return try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            val keyStoreInputStream = null // By convention, 'null' creates an empty key store.
            keyStore.load(keyStoreInputStream, password)
            keyStore
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        } catch (e: CertificateException) {
            throw AssertionError(e)
        } catch (e: IOException) {
            throw AssertionError(e)
        } catch (e: KeyStoreException) {
            throw AssertionError(e)
        }
    }
}