package com.ciandt.config;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.ciandt.schema.registry.SchemaRegistryProperties;
import com.ciandt.schema.registry.SchemaRegistrySSLSocketFactory;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Configuration
class SenderConfig {
	
	@Autowired
	KafkaProperties kafkaProperties;
	
	@Autowired
	SchemaRegistryProperties schemaRegistryProperties;
	
	@Bean
	public ProducerFactory<?, ?> producerFactory(final SchemaRegistrySSLSocketFactory schemaRegistrySSLSocketFactory) throws Exception {
		final RestService restService = new RestService(schemaRegistryProperties.getUrls());
		restService.setSslSocketFactory(schemaRegistrySSLSocketFactory.getSslSocketFactory());

		final SchemaRegistryClient client = new CachedSchemaRegistryClient(restService, 200);
		
		final Serializer<Object> valueSerializer = new KafkaAvroSerializer(client);
		final Serializer<String> keySerializer = new StringSerializer();
		
		return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(), keySerializer, valueSerializer);
	}

	@Bean
	public KafkaTemplate<String, GenericRecord> kafkaTemplate(final ProducerFactory<String, GenericRecord> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

}
