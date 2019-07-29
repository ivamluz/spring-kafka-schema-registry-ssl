package com.ciandt.schema.registry;

public final class SchemaRegistryConfig {

	private SchemaRegistryConfig() {
	}

	public static final String SSL_KEY_PASSWORD_CONFIG = "ssl.key-password";

	public static final String SSL_KEYSTORE_LOCATION_CONFIG = "ssl.trust-store.location";
	public static final String SSL_KEYSTORE_PASSWORD_CONFIG = "ssl.key-store.password";
	public static final String SSL_KEYSTORE_TYPE_CONFIG = "ssl.key-store.type";
	public static final String SSL_KEYMANAGER_ALGORITHM_CONFIG = "ssl.key-manager.algorithm";

	public static final String SSL_TRUSTSTORE_LOCATION_CONFIG = "ssl.trust-store.location";
	public static final String SSL_TRUSTSTORE_PASSWORD_CONFIG = "ssl.trust-store.password";
	public static final String SSL_TRUSTSTORE_TYPE_CONFIG = "ssl.trust-store.type";
	public static final String SSL_TRUSTMANAGER_ALGORITHM_CONFIG = "ssl.trust-manager.algorithm";

	public static final String SSL_PROTOCOL_CONFIG = "ssl.protocol";

	public static final String SSL_PROVIDER_CONFIG = "ssl.provider";

	public static final String URLS = "urls";

}
