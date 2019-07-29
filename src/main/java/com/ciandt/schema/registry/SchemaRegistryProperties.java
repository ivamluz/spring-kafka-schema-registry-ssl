package com.ciandt.schema.registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

@ConfigurationProperties(prefix = "spring.kafka.schema.registry")
public class SchemaRegistryProperties {

	private final Map<String, String> properties = new HashMap<>();

	private List<String> urls = new ArrayList<>(Collections.singletonList("localhost:8082"));

	private final Ssl ssl = new Ssl();

	public List<String> getUrls() {
		return this.urls;
	}

	public void setUrl(final List<String> urls) {
		this.urls = urls;
	}

	public Ssl getSsl() {
		return this.ssl;
	}

	public Map<String, Object> buildProperties() {

		final Map<String, Object> properties = new HashMap<>();

		if (this.urls != null) {
			properties.put(SchemaRegistryConfig.URLS, this.urls);
		}

		properties.putAll(this.ssl.buildProperties());

		if (!CollectionUtils.isEmpty(this.properties)) {
			properties.putAll(this.properties);
		}

		return properties;
	}

	public static class Ssl {

		private String keyPassword;

		private Resource keyStoreLocation;

		private String keyStorePassword;

		private String keyStoreType;

		private String keyManagerAlgorithm;

		private Resource trustStoreLocation;

		private String trustStorePassword;

		private String trustStoreType;

		private String trustManagerAlgorithm;

		private String protocol;

		private String provider;

		public String getKeyPassword() {
			return this.keyPassword;
		}

		public void setKeyPassword(String keyPassword) {
			this.keyPassword = keyPassword;
		}

		public Resource getKeyStoreLocation() {
			return this.keyStoreLocation;
		}

		public void setKeyStoreLocation(Resource keyStoreLocation) {
			this.keyStoreLocation = keyStoreLocation;
		}

		public String getKeyStorePassword() {
			return this.keyStorePassword;
		}

		public void setKeyStorePassword(String keyStorePassword) {
			this.keyStorePassword = keyStorePassword;
		}

		public String getKeyStoreType() {
			return this.keyStoreType;
		}

		public void setKeyStoreType(String keyStoreType) {
			this.keyStoreType = keyStoreType;
		}

		public String getKeyManagerAlgorithm() {
			return keyManagerAlgorithm;
		}

		public void setKeyManagerAlgorithm(String keyManagerAlgorithm) {
			this.keyManagerAlgorithm = keyManagerAlgorithm;
		}

		public Resource getTrustStoreLocation() {
			return this.trustStoreLocation;
		}

		public void setTrustStoreLocation(Resource trustStoreLocation) {
			this.trustStoreLocation = trustStoreLocation;
		}

		public String getTrustStorePassword() {
			return this.trustStorePassword;
		}

		public void setTrustStorePassword(String trustStorePassword) {
			this.trustStorePassword = trustStorePassword;
		}

		public String getTrustStoreType() {
			return this.trustStoreType;
		}

		public void setTrustStoreType(String trustStoreType) {
			this.trustStoreType = trustStoreType;
		}

		public String getTrustManagerAlgorithm() {
			return trustManagerAlgorithm;
		}

		public void setTrustManagerAlgorithm(String trustManagerAlgorithm) {
			this.trustManagerAlgorithm = trustManagerAlgorithm;
		}

		public String getProtocol() {
			return this.protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getProvider() {
			return this.provider;
		}

		public void setProvider(String provider) {
			this.provider = provider;
		}

		public Map<String, Object> buildProperties() {

			final Properties properties = new Properties();

			final PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();

			map.from(this::getKeyPassword).to(properties.in(SchemaRegistryConfig.SSL_KEY_PASSWORD_CONFIG));
			map.from(this::getProvider).to(properties.in(SchemaRegistryConfig.SSL_PROVIDER_CONFIG));
			map.from(this::getKeyManagerAlgorithm).to(properties.in(SchemaRegistryConfig.SSL_KEYMANAGER_ALGORITHM_CONFIG));
			map.from(this::getTrustManagerAlgorithm).to(properties.in(SchemaRegistryConfig.SSL_TRUSTMANAGER_ALGORITHM_CONFIG));
			map.from(this::getKeyStoreLocation).as(this::resourceToPath).to(properties.in(SchemaRegistryConfig.SSL_KEYSTORE_LOCATION_CONFIG));
			map.from(this::getKeyStorePassword).to(properties.in(SchemaRegistryConfig.SSL_KEYSTORE_PASSWORD_CONFIG));
			map.from(this::getKeyStoreType).to(properties.in(SchemaRegistryConfig.SSL_KEYSTORE_TYPE_CONFIG));
			map.from(this::getTrustStoreLocation).as(this::resourceToPath).to(properties.in(SchemaRegistryConfig.SSL_TRUSTSTORE_LOCATION_CONFIG));
			map.from(this::getTrustStorePassword).to(properties.in(SchemaRegistryConfig.SSL_TRUSTSTORE_PASSWORD_CONFIG));
			map.from(this::getTrustStoreType).to(properties.in(SchemaRegistryConfig.SSL_TRUSTSTORE_TYPE_CONFIG));
			map.from(this::getProtocol).to(properties.in(SchemaRegistryConfig.SSL_PROTOCOL_CONFIG));

			return properties;
		}

		private String resourceToPath(final Resource resource) {
			try {
				return resource.getFile().getAbsolutePath();
			} catch (IOException ex) {
				throw new IllegalStateException("Resource '" + resource + "' must be on a file system", ex);
			}
		}

	}

	private static class Properties extends HashMap<String, Object> {

		private static final long serialVersionUID = 7225521301736091815L;

		<V> java.util.function.Consumer<V> in(final String key) {
			return value -> put(key, value);
		}

	}

}
