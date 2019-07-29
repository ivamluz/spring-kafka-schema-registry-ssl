package com.ciandt.application;

import java.util.List;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ciandt.model.People;
import com.ciandt.repository.PeopleRepository;

@Service
public class PeopleApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(PeopleApplication.class);

	@Autowired
	KafkaTemplate<String, GenericRecord> kafkaTemplate;

	@Autowired
	PeopleRepository peopleRepository;

	@KafkaListener(topics = "people")
	public void receive(final GenericRecord payload) {
		LOGGER.info("received payload='{}'", payload);
	}

	@Transactional
	public List<People> findAll() {
		return peopleRepository.findAll();
	}

	@Transactional
	public People findById(String id) {
		return peopleRepository.findById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public People save(People payload) {
		LOGGER.info("sending payload='{}'", payload);

		final com.ciandt.avro.People people = new com.ciandt.avro.People();

		people.setBirthYear(payload.getBirthYear());
		people.setCreated(payload.getCreated().getTime());
		people.setEdited(payload.getEdited().getTime());
		people.setEyeColor(payload.getEyeColor());
		people.setGender(payload.getGender());
		people.setHairColor(payload.getHairColor());
		people.setHeight(payload.getHeight());
		people.setId(payload.getId());
		people.setMass(payload.getMass());
		people.setName(payload.getName());
		people.setSkinColor(payload.getSkinColor());

		final ProducerRecord<String, GenericRecord> producerRecord = new ProducerRecord<>("people",
				people.getId().toString(), people);

		kafkaTemplate.send(producerRecord);

		return payload;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public People update(String id, People people) {
		return peopleRepository.update(id, people);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String id) {
		peopleRepository.delete(id);
	}
}