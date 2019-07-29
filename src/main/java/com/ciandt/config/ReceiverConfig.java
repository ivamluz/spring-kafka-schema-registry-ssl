package com.ciandt.config;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.ciandt.schema.registry.SchemaRegistryProperties;
import com.ciandt.schema.registry.SchemaRegistrySSLSocketFactory;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

@EnableKafka
@Configuration
class ReceiverConfig {

	@Autowired
	KafkaProperties kafkaProperties;

	@Autowired
	SchemaRegistryProperties schemaRegistryProperties;

	@Bean
	public ConsumerFactory<?, ?> consumerFactory(final SchemaRegistrySSLSocketFactory schemaRegistrySSLSocketFactory)
			throws Exception {

		final RestService restService = new RestService(schemaRegistryProperties.getUrls());
		restService.setSslSocketFactory(schemaRegistrySSLSocketFactory.getSslSocketFactory());

		final SchemaRegistryClient client = new CachedSchemaRegistryClient(restService, 200);

		final Deserializer<Object> valueDeserializer = new KafkaAvroDeserializer(client);
		final Deserializer<String> keyDeserializer = new StringDeserializer();

		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), keyDeserializer,
				valueDeserializer);
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, GenericRecord>> containerFactory(
			final ConsumerFactory<String, GenericRecord> consumerFactory) {

		final ConcurrentKafkaListenerContainerFactory<String, GenericRecord> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();

		containerFactory.setConsumerFactory(consumerFactory);
		containerFactory.setConcurrency(20);

		return containerFactory;
	}

}
