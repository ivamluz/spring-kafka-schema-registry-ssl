package com.ciandt.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ciandt.model.People;

@Repository
public class PeopleRepository {

	private Map<String, People> repo = new HashMap<>();

	public People findById(String id) {
		return repo.get(id);
	}

	public List<People> findAll() {
		return new ArrayList<>(repo.values());
	}

	public People save(People people) {
		String id = UUID.randomUUID().toString();
		Date currentDate = new Date();

		people.setId(id);
		people.setCreated(currentDate);
		people.setEdited(currentDate);
		repo.put(id, people);

		return people;
	}

	public People update(String id, People people) {
		people.setId(id);
		people.setCreated(repo.get(id).getCreated());
		people.setEdited(new Date());
		repo.put(id, people);

		return people;
	}

	public void delete(String id) {
		repo.remove(id);
	}
}
