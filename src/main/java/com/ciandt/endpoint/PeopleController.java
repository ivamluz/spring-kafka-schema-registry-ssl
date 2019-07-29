package com.ciandt.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciandt.application.PeopleApplication;
import com.ciandt.model.People;
import com.ciandt.repository.PeopleRepository;

@RestController
@RequestMapping(path = "/api")
public class PeopleController {

	@Autowired
	PeopleRepository repo;

	@Autowired
	PeopleApplication peopleApplication;

	@GetMapping("/people")
	public List<People> findAll() {
		return peopleApplication.findAll();
	}

	@GetMapping("/people/{id}")
	public People findById(@PathVariable String id) {
		return peopleApplication.findById(id);
	}

	@PostMapping("/people")
	public People save(@RequestBody People people) {
		return peopleApplication.save(people);
	}

	@PutMapping(value = "/people/{id}")
	public People update(@PathVariable String id, @RequestBody People people) {
		return peopleApplication.update(id, people);
	}

	@DeleteMapping("/people/{id}")
	public void delete(@PathVariable String id) {
		peopleApplication.delete(id);
	}
}
