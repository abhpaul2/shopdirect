package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	PersonRepository ps; 
	
	@RequestMapping("/all")
	public List<Person> getAll() {
		return  ps.findAll();
	}
	
	@RequestMapping("{id}")
	public Person getPerson(@PathVariable("id") Integer id) {
		return ps.findOne(id);
	}

}
