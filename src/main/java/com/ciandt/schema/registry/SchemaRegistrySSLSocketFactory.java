package com.ciandt.schema.registry;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Optional;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public class SchemaRegistrySSLSocketFactory {

	private SchemaRegistryProperties properties;

	public SchemaRegistrySSLSocketFactory(final SchemaRegistryProperties properties) {
		this.properties = properties;
	}

	public SSLSocketFactory getSslSocketFactory() throws SSLException {
		try {

			final String protocol = Optional.ofNullable(properties.getSsl().getProtocol())
					.orElseThrow(() -> new IllegalArgumentException("ssl.protocol not found"));

			final KeyManager[] keyManagers = getKeyManagers();

			final TrustManager[] trustManagers = getTrustManagers();

			final SSLContext sslContext;

			if (StringUtils.isEmpty(properties.getSsl().getProvider())) {
				sslContext = SSLContext.getInstance(protocol);
			} else {
				sslContext = SSLContext.getInstance(protocol, properties.getSsl().getProvider());
			}

			sslContext.init(keyManagers, trustManagers, new SecureRandom());

			return sslContext.getSocketFactory();

		} catch (final Exception e) {

			throw new SSLException(e);
		}
	}

	private KeyManager[] getKeyManagers() throws NoSuchAlgorithmException, KeyStoreException, CertificateException,
			IOException, UnrecoverableKeyException {

		final String keyManagerAlgorithm = StringUtils.isEmpty(properties.getSsl().getKeyManagerAlgorithm())
				? KeyManagerFactory.getDefaultAlgorithm()
				: properties.getSsl().getKeyManagerAlgorithm();

		final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keyManagerAlgorithm);

		final KeyStore keyStore = createKeyStore();

		final String keyPassword = properties.getSsl().getKeyPassword();
		final String keyStorePassword = properties.getSsl().getKeyStorePassword();

		if (StringUtils.isEmpty(keyPassword) && StringUtils.isEmpty(keyStorePassword)) {
			throw new IllegalArgumentException("ssl.key-password and ssl.key-store.password not found");
		}

		final char[] passwordChars = StringUtils.isEmpty(keyPassword) ? keyStorePassword.toCharArray()
				: keyPassword.toCharArray();

		keyManagerFactory.init(keyStore, passwordChars);

		return keyManagerFactory.getKeyManagers();
	}

	private TrustManager[] getTrustManagers()
			throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {

		final String trustManagerAlgorithm = StringUtils.isEmpty(properties.getSsl().getTrustManagerAlgorithm())
				? TrustManagerFactory.getDefaultAlgorithm()
				: properties.getSsl().getTrustManagerAlgorithm();

		final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerAlgorithm);

		final KeyStore trustStore = createTrustStore();

		trustManagerFactory.init(trustStore);

		return trustManagerFactory.getTrustManagers();
	}

	private KeyStore createKeyStore()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		final KeyStore keyStore = KeyStore.getInstance(properties.getSsl().getKeyStoreType());

		final String password = properties.getSsl().getKeyStorePassword();
		final char[] passwordChars = StringUtils.isEmpty(password) ? null : password.toCharArray();

		final Resource keyStoreLocation = Optional.ofNullable(properties.getSsl().getKeyStoreLocation())
				.orElseThrow(() -> new IllegalArgumentException("ssl.key-store-location not found"));

		keyStore.load(keyStoreLocation.getInputStream(), passwordChars);

		return keyStore;
	}

	private KeyStore createTrustStore()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		final KeyStore keyStore = KeyStore.getInstance(properties.getSsl().getTrustStoreType());

		final String password = properties.getSsl().getTrustStorePassword();
		final char[] passwordChars = StringUtils.isEmpty(password) ? null : password.toCharArray();

		final Resource trustStoreLocation = Optional.ofNullable(properties.getSsl().getTrustStoreLocation())
				.orElseThrow(() -> new IllegalArgumentException("ssl.trust-store-location not found"));

		keyStore.load(trustStoreLocation.getInputStream(), passwordChars);

		return keyStore;
	}

}
