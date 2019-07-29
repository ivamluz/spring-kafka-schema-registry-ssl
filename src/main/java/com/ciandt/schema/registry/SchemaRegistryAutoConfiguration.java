package com.ciandt.schema.registry;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SchemaRegistryProperties.class)
public class SchemaRegistryAutoConfiguration {

	private final SchemaRegistryProperties properties;

	public SchemaRegistryAutoConfiguration(final SchemaRegistryProperties properties) {
		this.properties = properties;
	}
	
	@Bean
	@ConditionalOnMissingBean(SchemaRegistrySSLSocketFactory.class)
	public SchemaRegistrySSLSocketFactory schemaRegistrySSLSocketFactory() {
		return new SchemaRegistrySSLSocketFactory(this.properties);
	}
	
}
