package com.ciandt.schema.registry;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class SchemaRegistrySSLSocketFactoryTest {

	private final SchemaRegistryProperties properties = new SchemaRegistryProperties();

	@Before
	public void SetUp() {
		properties.getSsl().setProtocol("SSL");
		properties.getSsl().setKeyPassword("changeit");
		properties.getSsl().setKeyStoreLocation(new ClassPathResource("keystore-test.jks"));
		properties.getSsl().setKeyStorePassword("changeit");
		properties.getSsl().setKeyManagerAlgorithm("SunX509");
		properties.getSsl().setKeyStoreType("JKS");
		properties.getSsl().setTrustStoreLocation(new ClassPathResource("truststore-test.jks"));
		properties.getSsl().setTrustStorePassword("changeit");
		properties.getSsl().setTrustManagerAlgorithm("SunX509");
		properties.getSsl().setTrustStoreType("JKS");
	}

	@Test
	public void whenMinimalValidConfigurationGetSslSocketFactorySuccess() throws SSLException {

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		final SSLSocketFactory sslSocketFactory = factory.getSslSocketFactory();

		assertThat(sslSocketFactory, is(notNullValue()));
	}

	@Test(expected = SSLException.class)
	public void whenInvalidKeyPassworConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyPassword("xpto");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenInvalidProtocolConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setProtocol("XPTO");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenInvalidKeyStoreLocationConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyStoreLocation(new ClassPathResource("tmp/keystore-test.jks"));

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenInvalidKeyStorePasswordConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyStorePassword("xpto");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenInvalidKeyStoreTypeConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyStoreType("TXT");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}
	
	@Test(expected = SSLException.class)
	public void whenInvalidKeyManagerAlgorithmConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyManagerAlgorithm("XPTO");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenNullKeyStoreLocationConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyStoreLocation(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test
	public void whenNullKeyStorePasswordConfigurationGetSslSocketFactorySuccess() throws SSLException {

		properties.getSsl().setKeyStorePassword(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		final SSLSocketFactory sslSocketFactory = factory.getSslSocketFactory();

		assertThat(sslSocketFactory, is(notNullValue()));
	}

	@Test(expected = SSLException.class)
	public void whenNullKeyPasswordAndKeyStorePasswordConfigurationGetSslSocketFactorySuccess() throws SSLException {

		properties.getSsl().setKeyPassword(null);
		properties.getSsl().setKeyStorePassword(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenNullKeyStoreTypeConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyStoreType(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}
	
	@Test
	public void whenNullKeyManagerAlgorithmConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setKeyManagerAlgorithm(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		final SSLSocketFactory sslSocketFactory = factory.getSslSocketFactory();

		assertThat(sslSocketFactory, is(notNullValue()));
	}

	@Test(expected = SSLException.class)
	public void whenInvalidTrustStoreLocationConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setTrustStoreLocation(new ClassPathResource("tmp/keystore-test.jks"));

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenInvalidTrustStorePasswordConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setTrustStorePassword("xpto");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenInvalidTrustStoreTypeConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setTrustStoreType("TXT");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}
	
	@Test(expected = SSLException.class)
	public void whenInvalidTrustManagerAlgorithmConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setTrustManagerAlgorithm("XPTO");

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test(expected = SSLException.class)
	public void whenNullTrustStoreLocationConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setTrustStoreLocation(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}

	@Test
	public void whenNullTrustStorePasswordConfigurationGetSslSocketFactorySuccess() throws SSLException {

		properties.getSsl().setTrustStorePassword(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		final SSLSocketFactory sslSocketFactory = factory.getSslSocketFactory();

		assertThat(sslSocketFactory, is(notNullValue()));
	}

	@Test(expected = SSLException.class)
	public void whenNullTrustStoreTypeConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setTrustStoreType(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		factory.getSslSocketFactory();
	}
	
	@Test
	public void whenNullTrustManagerAlgorithmConfigurationGetSslSocketFactoryFail() throws SSLException {

		properties.getSsl().setTrustManagerAlgorithm(null);

		final SchemaRegistrySSLSocketFactory factory = new SchemaRegistrySSLSocketFactory(properties);

		final SSLSocketFactory sslSocketFactory = factory.getSslSocketFactory();

		assertThat(sslSocketFactory, is(notNullValue()));
	}

}
